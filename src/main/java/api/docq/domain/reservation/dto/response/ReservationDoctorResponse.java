package api.docq.domain.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class ReservationDoctorResponse {

    private final Long reservationId;

    private final String userName;

    private final LocalTime time;

    private final String message;

    private final LocalDateTime createdAt;

    @Builder
    private ReservationDoctorResponse(Long reservationId, String userName, LocalTime time, String message, LocalDateTime createdAt) {
        this.reservationId = reservationId;
        this.userName = userName;
        this.time = time;
        this.message = message;
        this.createdAt = createdAt;
    }

    public static ReservationDoctorResponse of(Long reservationId, String userName, LocalTime time, String message, LocalDateTime createdAt) {
        return ReservationDoctorResponse.builder()
                .reservationId(reservationId)
                .userName(userName)
                .time(time)
                .message(message)
                .createdAt(createdAt)
                .build();
    }
}
