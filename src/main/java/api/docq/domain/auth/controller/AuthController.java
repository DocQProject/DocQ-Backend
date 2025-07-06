package api.docq.domain.auth.controller;

import api.docq.domain.auth.dto.request.SignInRequest;
import api.docq.domain.auth.dto.request.SignUpRequest;
import api.docq.domain.auth.dto.response.SignInResponse;
import api.docq.domain.auth.dto.response.SignUpResponse;
import api.docq.domain.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    /**
     * 회원가입
     */
    @PostMapping("/sign-up")
    public ResponseEntity<SignUpResponse> signUp(
            @Valid @RequestBody SignUpRequest signUpRequest
    ) {
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    /**
     * 로그인
     */
    @PostMapping("/sign-in")
    public ResponseEntity<SignInResponse> signIn(
            @Valid @RequestBody SignInRequest signInRequest
    ) {
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }
}
