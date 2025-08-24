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
    private User(String loginId, String name, String email, String password, UserRole role) {
        this.clinicId = null;
        this.loginId = loginId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isDeleted = false;
    }

    public static User of(String loginId, String name, String email, String password, UserRole role) {
        return User.builder()
                .loginId(loginId)
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .build();
    }

    public void updateProfile(String loginId, String name, String password, String email) {
        this.loginId = loginId;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public void updateClinic(Long clinicId) {
        this.clinicId = clinicId;
    }

    public void delete() {
        this.isDeleted = true;
    }
}
