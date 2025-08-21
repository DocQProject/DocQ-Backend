package api.docq.domain.auth.service;

import api.docq.common.exception.ConflictException;
import api.docq.common.exception.NotFoundException;
import api.docq.common.exception.UnauthorizedException;
import api.docq.config.security.JwtProvider;
import api.docq.domain.auth.dto.request.LoginIdCheckRequest;
import api.docq.domain.auth.dto.request.SignInRequest;
import api.docq.domain.auth.dto.request.SignUpRequest;
import api.docq.domain.auth.dto.response.SignInResponse;
import api.docq.domain.auth.dto.response.SignUpResponse;
import api.docq.domain.clinic.repository.ClinicRepository;
import api.docq.domain.user.entity.User;
import api.docq.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static api.docq.common.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final ClinicRepository clinicRepository;

    @Transactional
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByLoginId(signUpRequest.getLoginId())) {
            throw new ConflictException(ALREADY_EXISTS_LOGIN_ID);
        }

        if (!signUpRequest.getPassword().equals(signUpRequest.getCheckPassword())) {
            throw new UnauthorizedException(PASSWORD_MISMATCH);
        }

        String encodePassword = passwordEncoder.encode(signUpRequest.getPassword());

        User user = User.of(
                signUpRequest.getLoginId(),
                signUpRequest.getName(),
                signUpRequest.getEmail(),
                encodePassword,
                signUpRequest.getRole()
        );

        userRepository.save(user);
        String accessToken = jwtProvider.createAccessToken(user.getId(), user.getLoginId(), user.getName(), user.getRole());

        return SignUpResponse.of(accessToken);
    }

    @Transactional(readOnly = true)
    public SignInResponse signIn(SignInRequest signInRequest) {
        User user = userRepository.findByLoginId(signInRequest.getLoginId())
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_USER));

        if (!passwordEncoder.matches(signInRequest.getPassword(), user.getPassword())) {
            throw new UnauthorizedException(PASSWORD_MISMATCH);
        }

        String accessToken = jwtProvider.createAccessToken(user.getId(), user.getLoginId(), user.getName(), user.getRole());

        System.out.println(accessToken);

        return SignInResponse.of(accessToken);
    }

    public Boolean checkLoginIdAvailability(LoginIdCheckRequest loginIdCheckRequest) {

        if (userRepository.existsByLoginId(loginIdCheckRequest.getLoginId())) {
            throw new ConflictException(ALREADY_EXISTS_LOGIN_ID);
        }

        return true;
    }
}
