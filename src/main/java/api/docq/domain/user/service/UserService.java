package api.docq.domain.user.service;

import api.docq.common.dto.AuthUser;
import api.docq.domain.user.dto.request.UserUpdateClinicRequest;
import api.docq.domain.user.dto.request.UserUpdatePasswordRequest;
import api.docq.domain.user.dto.request.UserUpdateProfileRequest;
import api.docq.domain.user.dto.response.UserResponse;
import api.docq.domain.user.entity.User;
import api.docq.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void updatePassword(AuthUser authUser, UserUpdatePasswordRequest request) {
        User user = userRepository.findByLoginId(authUser.getLoginId())
                .orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("기존 비밀번호가 일치하지 않습니다.");
        }

        if (request.getPassword().equals(request.getNewPassword())) {
            throw new RuntimeException("새 비밀번호는 기존 비밀번화 일치할 수 없습니다.");
        }

        String encodePassword = passwordEncoder.encode(request.getNewPassword());
        user.updatePassword(encodePassword);
    }

    @Transactional
    public void updateProfile(AuthUser authUser, UserUpdateProfileRequest request) {
        User user = userRepository.findByLoginId(authUser.getLoginId())
                .orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));

        user.updateNameAndEmail(request.getName(), request.getEmail());
    }

    @Transactional
    public void updateClinic(AuthUser authUser, UserUpdateClinicRequest request) {
        User user = userRepository.findByLoginId(authUser.getLoginId())
                .orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));

        //todo: Clinic이 존재하는지 확인하는 조건 추가
        user.updateClicnic(request.getClinicId());
    }

    public UserResponse findUser(AuthUser authUser) {
        User user = userRepository.findByLoginId(authUser.getLoginId())
                .orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));

        return UserResponse.of(
                user.getId(),
                user.getLoginId(),
                user.getName(),
                user.getEmail(),
                user.getClinicId(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}
