package api.docq.domain.reservation.entity;

import api.docq.common.entity.TimeStamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@Entity
@Table(name = "reservations")
@NoArgsConstructor
public class Reservation extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clinicId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalTime time;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private boolean isDeleted;

    @Builder
    private Reservation (Long clinicId, Long userId, LocalTime time, String message) {
        this.clinicId = clinicId;
        this.userId = userId;
        this.time = time;
        this.message = message;
        this.isDeleted = false;
    }

    public static Reservation of(Long clinicId, Long userId, LocalTime time, String message) {
        return Reservation.builder()
                .clinicId(clinicId)
                .userId(userId)
                .time(time)
                .message(message)
                .build();
    }

    public void delete() {
        this.isDeleted = true;
    }
}
