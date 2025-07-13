package api.docq.domain.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;
import java.util.List;

@Getter
public class ReservationTimeRespone {
    private final List<String> times;

    @Builder
    private ReservationTimeRespone (List<String> times) {
        this.times = times;
    }

    public static ReservationTimeRespone of (List<String> times) {
        return ReservationTimeRespone.builder()
                .times(times)
                .build();
    }
}
