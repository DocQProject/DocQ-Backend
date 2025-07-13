package api.docq.domain.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class ReservationUserResponse {

    private final Long reservationId;

    private final String userName;

    private final String clinicName;

    private final LocalTime time;

    private final String message;

    private final LocalDateTime createdAt;

    private final boolean isDeleted;

    @Builder
    private ReservationUserResponse(Long reservationId, String userName, String clinicName, LocalTime time, String message, LocalDateTime createdAt, boolean isDeleted) {
        this.reservationId = reservationId;
        this.userName = userName;
        this.clinicName = clinicName;
        this.time = time;
        this.message = message;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
    }

    public static ReservationUserResponse of(Long reservationId, String userName, String clinicName, LocalTime time, String message, LocalDateTime createdAt, boolean isDeleted) {
        return ReservationUserResponse.builder()
                .reservationId(reservationId)
                .userName(userName)
                .clinicName(clinicName)
                .time(time)
                .message(message)
                .createdAt(createdAt)
                .isDeleted(isDeleted)
                .build();
    }
}
