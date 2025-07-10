package api.docq.domain.clinic.controller;

import api.docq.common.dto.AuthUser;
import api.docq.domain.clinic.dto.request.ClinicCreateRequest;
import api.docq.domain.clinic.dto.response.ClinicRespone;
import api.docq.domain.clinic.service.ClinicService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clinics")
public class ClinicController {

    private final ClinicService clinicService;

    /**
     * 병원 생성
     */
    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PostMapping
    public ResponseEntity<ClinicRespone> createClinic(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody ClinicCreateRequest request
    ) {
        return ResponseEntity.ok(clinicService.createClinic(authUser.getUserId(), request));
    }
}
