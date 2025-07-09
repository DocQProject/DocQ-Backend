package api.docq.common.dto;

import api.docq.domain.user.enums.UserRole;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
public class AuthUser {
    private final Long userId;
    private final String loginId;
    private final String name;
    private final Collection<? extends GrantedAuthority> authorities;

    @Builder
    private AuthUser(Long userId, String loginId,String name, UserRole userRole) {
        this.userId = userId;
        this.loginId= loginId;
        this.name = name;
        this.authorities = List.of(new SimpleGrantedAuthority(userRole.name()));
    }

    public static AuthUser of(Long userId, String loginId, String name, UserRole userRole) {
        return AuthUser.builder()
                .userId(userId)
                .loginId(loginId)
                .name(name)
                .userRole(userRole)
                .build();
    }

    public boolean hasRole(String role) {
        return authorities.stream()
                .anyMatch(authority -> authority.getAuthority().equals(role));
    }
}
