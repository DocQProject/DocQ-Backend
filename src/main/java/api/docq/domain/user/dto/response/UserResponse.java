package api.docq.domain.user.dto.response;

import api.docq.domain.user.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponse {
    private final Long userId;
    private final String loginId;
    private final String name;
    private final String email;
    private final String clinicName;
    private final UserRole role;
    private final LocalDateTime createdAt;

    @Builder
    private UserResponse(Long userId, String loginId, String name, String email, String clinicName, UserRole role, LocalDateTime createdAt) {
        this.userId = userId;
        this.loginId = loginId;
        this.name = name;
        this.email = email;
        this.clinicName = clinicName;
        this.role = role;
        this.createdAt = createdAt;
    }

    public static UserResponse of(Long userId, String loginId, String name, String email, String clinicName, UserRole role, LocalDateTime createdAt) {
        return UserResponse.builder()
                .userId(userId)
                .loginId(loginId)
                .name(name)
                .email(email)
                .clinicName(clinicName)
                .role(role)
                .createdAt(createdAt)
                .build();
    }
}
