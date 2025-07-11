package api.docq.domain.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
public class ReservationTimeRespone {
    private final List<LocalTime> times;

    @Builder
    private ReservationTimeRespone (List<LocalTime> times) {
        this.times = times;
    }

    public static ReservationTimeRespone of (List<LocalTime> times) {
        return ReservationTimeRespone.builder()
                .times(times)
                .build();
    }
}
