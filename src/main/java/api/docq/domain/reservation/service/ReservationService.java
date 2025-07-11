package api.docq.domain.reservation.service;

import api.docq.domain.clinic.entity.Clinic;
import api.docq.domain.clinic.repository.ClinicRepository;
import api.docq.domain.reservation.dto.request.ReservationRequest;
import api.docq.domain.reservation.dto.response.ReservationDoctorResponse;
import api.docq.domain.reservation.dto.response.ReservationResponse;
import api.docq.domain.reservation.dto.response.ReservationUserResponse;
import api.docq.domain.reservation.entity.Reservation;
import api.docq.domain.reservation.repository.ReservationRepository;
import api.docq.domain.user.entity.User;
import api.docq.domain.user.repository.UserRepository;
import api.docq.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;

    @Transactional
    public ReservationResponse createReservation(Long userId,String userName, Long clinicId, ReservationRequest request) {
        userService.existsByUserId(userId);
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new RuntimeException("병원이 존재하지 않습니다."));

        if (request.getTime().isBefore(clinic.getOpenTime()) || request.getTime().isAfter(clinic.getCloseTime())) {
            throw new RuntimeException("예약 시간이 병원 운영 시간 외입니다.");
        }

        if (reservationRepository.existsByClinicIdAndTime(clinicId, request.getTime())) {
            throw new RuntimeException("해당 시간에는 이미 예약이 존재합니다.");
        }

        Reservation reservation = Reservation.of(
                clinicId,
                userId,
                request.getTime(),
                request.getMessage()
        );

        reservationRepository.save(reservation);

        return ReservationResponse.of(
                reservation.getId(),
                userName,
                clinic.getName(),
                request.getTime(),
                request.getMessage(),
                reservation.getCreatedAt()
                );
    }

    @Transactional(readOnly = true)
    public Page<ReservationUserResponse> getReservations(Long userId, Pageable pageable) {
        User user = userService.findUserByUserIdOrElseThrow(userId);
        Page<Reservation> reservations = reservationRepository.findAllByUserId(userId, pageable);

        List<Long> clinicIds = reservations.getContent().stream()
                .map(Reservation::getClinicId)
                .distinct()
                .toList();

        Map<Long, Clinic> clinicMap = clinicRepository.findAllById(clinicIds).stream()
                .collect(Collectors.toMap(Clinic::getId, Function.identity()));

        return reservations.map(reservation -> {
            Clinic clinic = clinicMap.get(reservation.getClinicId());

            return ReservationUserResponse.of(
                    reservation.getId(),
                    user.getName(),
                    clinic.getName(),
                    reservation.getTime(),
                    reservation.getMessage(),
                    reservation.getCreatedAt(),
                    reservation.isDeleted()
            );
        });
    }

    @Transactional(readOnly = true)
    public Page<ReservationDoctorResponse> getReservationsByDoctor(Long userId, Pageable pageable) {
        User user = userService.findUserByUserIdOrElseThrow(userId);

        if (user.getClinicId() == null) {
            throw new RuntimeException("소속된 병원이 없습니다.");
        }

        Page<Reservation> reservations = reservationRepository.findAllByClinicId(user.getClinicId(), pageable);

        List<Long> userIds = reservations.getContent().stream()
                .map(Reservation::getUserId)
                .distinct()
                .toList();

        Map<Long, User> userMap = userRepository.findAllById(userIds).stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return reservations.map(reservation -> {
            String userName = userMap.get(reservation.getUserId()).getName();

            return ReservationDoctorResponse.of(
                    reservation.getId(),
                    userName,
                    reservation.getTime(),
                    reservation.getMessage(),
                    reservation.getCreatedAt()
            );
        });
    }

    @Transactional
    public void deleteReservations(Long userId, Long reservationId) {
        userService.existsByUserId(userId);

        Reservation reservation = reservationRepository.findByUserIdAndReservationId(userId, reservationId);
        reservation.delete();
    }
}
