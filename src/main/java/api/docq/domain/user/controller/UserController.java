package api.docq.domain.user.controller;

import api.docq.common.dto.AuthUser;
import api.docq.domain.user.dto.request.UserDeleteRequest;
import api.docq.domain.user.dto.request.UserUpdatePasswordRequest;
import api.docq.domain.user.dto.request.UserUpdateProfileRequest;
import api.docq.domain.user.dto.response.UserGetResponse;
import api.docq.domain.user.dto.response.UserResponse;
import api.docq.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * 유저 비밀번호 변경하기
     */
    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody UserUpdatePasswordRequest request
    ) {
        userService.updatePassword(authUser.getUserId(), request);
        return ResponseEntity.ok().build();
    }

    /**
     * 유저 조회하기 (본인용)
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUser(
            @AuthenticationPrincipal AuthUser authUser
    ) {
        return ResponseEntity.ok(userService.findUser(authUser.getUserId()));
    }

    /**
     * 유저 조회하기 (관리자용)
     */
    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Page<UserGetResponse>> getUsers(
            Pageable pageable
    ) {
        return ResponseEntity.ok(userService.getUsers(pageable));
    }

    /**
     * 유저 프로필 변경하기(name, email)
     */
    @PatchMapping("/profile")
    public ResponseEntity<Void> updateProfile(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody UserUpdateProfileRequest request
    ) {
        userService.updateProfile(authUser.getUserId(), request);
        return ResponseEntity.ok().build();
    }

    /**
     * 유저 병원 변경하기
     */
    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PatchMapping("/clinics/{clinicId}")
    public ResponseEntity<Void> updateClinic(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long clinicId
    ) {
        userService.updateClinic(authUser.getUserId(), clinicId);
        return ResponseEntity.ok().build();
    }

    /**
     * 유저 탈퇴하기
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody UserDeleteRequest request
    ) {
        userService.deleteUser(authUser.getUserId(), request);
        return ResponseEntity.ok().build();
    }
}
