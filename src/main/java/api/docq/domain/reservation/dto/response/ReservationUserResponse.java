package api.docq.domain.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class ReservationUserResponse {

    private final Long reservationId;

    private final String userName;

    private final String clinicName;

    private final LocalDate date;

    private final LocalTime time;

    private final String message;

    private final boolean isDeleted;

    @Builder
    private ReservationUserResponse(Long reservationId, String userName, String clinicName, LocalDate date, LocalTime time, String message, boolean isDeleted) {
        this.reservationId = reservationId;
        this.userName = userName;
        this.clinicName = clinicName;
        this.time = time;
        this.message = message;
        this.date = date;
        this.isDeleted = isDeleted;
    }

    public static ReservationUserResponse of(Long reservationId, String userName, String clinicName, LocalDate date, LocalTime time, String message, boolean isDeleted) {
        return ReservationUserResponse.builder()
                .reservationId(reservationId)
                .userName(userName)
                .clinicName(clinicName)
                .time(time)
                .message(message)
                .date(date)
                .isDeleted(isDeleted)
                .build();
    }
}
