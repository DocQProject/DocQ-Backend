package api.docq.domain.user.controller;

import api.docq.common.dto.AuthUser;
import api.docq.domain.user.dto.request.UserUpdatePasswordRequest;
import api.docq.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * 유저 비밀번호 변경하기
     */
    @PreAuthorize("hasAnyRole('USER', 'DOCTOR')")
    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody UserUpdatePasswordRequest request
    ) {
        userService.updatePassword(authUser, request);
        return ResponseEntity.ok().build();
    }
}
