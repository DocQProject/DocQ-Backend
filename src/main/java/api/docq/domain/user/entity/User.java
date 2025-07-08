package api.docq.domain.user.entity;

import api.docq.common.entity.TimeStamped;
import api.docq.domain.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long clinicId;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole role ;

    @Column(nullable = false)
    private boolean isDeleted;

    @Builder
    private User(Long clinicId, String loginId, String name, String email, String password, UserRole role) {
        this.clinicId = clinicId;
        this.loginId = loginId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isDeleted = false;
    }

    public static User of(Long clinicId, String loginId, String name, String email, String password, UserRole role) {
        return User.builder()
                .clinicId(clinicId)
                .loginId(loginId)
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }

    public void updateNameAndEmail(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public void updateClicnic(Long clinicId) {
        this.clinicId = clinicId;
    }
}
