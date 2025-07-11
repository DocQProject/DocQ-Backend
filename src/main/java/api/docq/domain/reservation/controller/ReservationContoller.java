package api.docq.domain.reservation.controller;

import api.docq.common.dto.AuthUser;
import api.docq.domain.reservation.dto.request.ReservationRequest;
import api.docq.domain.reservation.dto.response.ReservationResponse;
import api.docq.domain.reservation.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReservationContoller {

    private final ReservationService reservationService;

    /**
     * 예약 생성하기
     */
    @PreAuthorize("hasAnyRole('USER')")
    @PostMapping("/clinics/{clinicId}/reservations")
    public ResponseEntity<ReservationResponse> createReservation(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long clinicId,
            @Valid @RequestBody ReservationRequest request
    ) {
        return ResponseEntity.ok(reservationService.createReservation(authUser.getUserId(), authUser.getName(), clinicId, request));
    }
}
