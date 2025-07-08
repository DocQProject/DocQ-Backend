package api.docq.domain.user.dto.response;

import api.docq.domain.user.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserGetResponse {
    private final Long userId;
    private final String loginId;
    private final String name;
    private final String email;
    private final Long clinicId;
    private final UserRole role;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final boolean isDeleted;


    @Builder
    private UserGetResponse(Long userId, String loginId, String name, String email, Long clinicId, UserRole role, LocalDateTime createdAt, LocalDateTime modifiedAt, boolean isDeleted) {
        this.userId = userId;
        this.loginId = loginId;
        this.name = name;
        this.email = email;
        this.clinicId = clinicId;
        this.role = role;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.isDeleted = isDeleted;
    }

    public static UserGetResponse of(Long userId, String loginId, String name, String email, Long clinicId, UserRole role, LocalDateTime createdAt, LocalDateTime modifiedAt, boolean isDeleted) {
        return UserGetResponse.builder()
                .userId(userId)
                .loginId(loginId)
                .name(name)
                .email(email)
                .clinicId(clinicId)
                .role(role)
                .createdAt(createdAt)
                .modifiedAt(modifiedAt)
                .isDeleted(isDeleted)
                .build();
    }
}
