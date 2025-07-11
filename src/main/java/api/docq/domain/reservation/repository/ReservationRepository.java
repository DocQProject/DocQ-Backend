package api.docq.domain.reservation.repository;

import api.docq.domain.reservation.entity.Reservation;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByClinicIdAndTime(Long clinicId, LocalTime time);
}
