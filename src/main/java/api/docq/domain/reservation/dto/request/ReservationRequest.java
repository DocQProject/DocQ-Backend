package api.docq.domain.reservation.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class ReservationRequest {

    @NotNull(message = "예약 날짜를 입력해주세요.")
    @JsonFormat(pattern = "yyyy.MM.dd")
    private final LocalDate date;

    @NotNull(message = "예약 시간을 입력해주세요.")
    @DateTimeFormat(pattern = "HH:mm")
    private final LocalTime time;

    private final String message;
}
