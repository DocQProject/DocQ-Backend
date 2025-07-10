package api.docq.domain.clinic.controller;

import api.docq.common.dto.AuthUser;
import api.docq.domain.clinic.dto.request.ClinicCreateRequest;
import api.docq.domain.clinic.dto.response.ClinicResponse;
import api.docq.domain.clinic.service.ClinicService;
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
@RequestMapping("/api/clinics")
public class ClinicController {

    private final ClinicService clinicService;

    /**
     * 병원 생성
     */
    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PostMapping
    public ResponseEntity<ClinicResponse> createClinic(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody ClinicCreateRequest request
    ) {
        return ResponseEntity.ok(clinicService.createClinic(authUser.getUserId(), request));
    }

    /**
     * 병원 단건 조회
     */
    @GetMapping("/{clinicId}")
    public ResponseEntity<ClinicResponse> findClinic(
            @PathVariable Long clinicId
    ) {
        return ResponseEntity.ok(clinicService.findClinic(clinicId));
    }

    /**
     * 병원 다건 조회
     */
    @GetMapping
    public ResponseEntity<Page<ClinicResponse>> getClinic(
            Pageable pageable
    ) {
        return ResponseEntity.ok(clinicService.getClinic(pageable));
    }
}
