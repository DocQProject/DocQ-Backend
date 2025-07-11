package api.docq.domain.reservation.repository;

import api.docq.domain.reservation.entity.Reservation;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByClinicIdAndTime(Long clinicId, LocalTime time);

    @Query("SELECT r FROM Reservation r WHERE r.userId = :userId")
    Page<Reservation> findAllByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT r FROM Reservation r WHERE r.clinicId = :clinicId")
    Page<Reservation> findAllByClinicId(@Param("clinicId") Long clinicId, Pageable pageable);
}
