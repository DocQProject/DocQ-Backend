package api.docq.domain.reservation.repository;

import api.docq.domain.clinic.entity.Clinic;
import api.docq.domain.reservation.entity.Reservation;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    boolean existsByClinicIdAndDateAndTime(Long clinicId, LocalDate date, LocalTime time);

    @Query("SELECT r FROM Reservation r WHERE r.userId = :userId")
    Page<Reservation> findAllByUserId(@Param("userId") Long userId, Pageable pageable);

    @Query("SELECT r FROM Reservation r WHERE r.isDeleted IS NOT true AND r.clinicId = :clinicId")
    Page<Reservation> findAllByClinicId(@Param("clinicId") Long clinicId, Pageable pageable);

    @Query("SELECT r FROM Reservation r WHERE r.isDeleted IS NOT true AND r.userId = :userId AND r.id = :reservationId")
    Reservation findByUserIdAndReservationId(@Param("userId") Long userId, @Param("reservationId") Long reservationId);

    @Query("SELECT r.time FROM Reservation r WHERE r.isDeleted IS NOT true AND r.clinicId = :clinicId AND r.date = :date")
    List<LocalTime> findAllTimeByClinicIdAndDate(@Param("clinicId")Long clinicId, @Param("date") LocalDate date);
}
