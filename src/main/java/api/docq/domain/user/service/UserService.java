package api.docq.domain.user.service;

import api.docq.common.exception.ErrorCode;
import api.docq.common.exception.UnauthorizedException;
import api.docq.domain.clinic.entity.Clinic;
import api.docq.domain.clinic.repository.ClinicRepository;
import api.docq.domain.user.dto.request.UserDeleteRequest;
import api.docq.domain.user.dto.request.UserProfileUpdateRequest;
import api.docq.domain.user.dto.response.UserGetResponse;
import api.docq.domain.user.dto.response.UserResponse;
import api.docq.domain.user.entity.User;
import api.docq.domain.user.enums.UserRole;
import api.docq.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ClinicRepository clinicRepository;

    @Transactional
    public void updateProfile(Long userId, UserProfileUpdateRequest request) {
        User user = findUserByUserIdOrElseThrow(userId);

        if (userRepository.existsByLoginId(request.getLoginId())) {
            throw new UnauthorizedException(ErrorCode.ALREADY_EXISTS_USER);
        }

        user.updateProfile(request.getLoginId(), request.getName(), passwordEncoder.encode(request.getPassword()), request.getEmail());
    }

    @Transactional
    public void updateClinic(Long userId, Long clinicId) {
        User user = findUserByUserIdOrElseThrow(userId);

        if (!clinicRepository.existsById(clinicId)) {
            throw new RuntimeException("병원이 존재하지 않습니다.");
        }

        user.updateClinic(clinicId);
    }

    @Transactional(readOnly = true)
    public UserResponse findUser(Long userId) {
        User user = findUserByUserIdOrElseThrow(userId);
        String clinicName = "";

        if (UserRole.ROLE_DOCTOR.equals(user.getRole())) {

            if (user.getClinicId() != null) {
                Clinic clinic = clinicRepository.findById(user.getClinicId())
                        .orElseThrow(() -> new RuntimeException("병원을 찾을 수 없습니다."));

                clinicName = clinic.getName();
            } else {

                clinicName = null;
            }
        }

        return UserResponse.of(
                user.getId(),
                user.getLoginId(),
                user.getName(),
                user.getEmail(),
                clinicName,
                user.getRole(),
                user.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<UserGetResponse> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(user -> UserGetResponse.of(
                        user.getId(),
                        user.getLoginId(),
                        user.getName(),
                        user.getEmail(),
                        user.getClinicId(),
                        user.getRole(),
                        user.getCreatedAt(),
                        user.getUpdatedAt(),
                        user.isDeleted())
                );
    }

    @Transactional
    public void deleteUser(Long userId, UserDeleteRequest request) {
        User user = findUserByUserIdOrElseThrow(userId);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }

        user.delete();
    }

    public User findUserByUserIdOrElseThrow(Long userId) {
        return userRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("유저가 존재하지 않습니다."));
    }

    public void existsByUserId(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("유저가 존재하지 않습니다.");
        }
    }
}
