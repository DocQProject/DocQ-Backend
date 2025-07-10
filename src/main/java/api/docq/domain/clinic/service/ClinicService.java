package api.docq.domain.clinic.service;

import api.docq.domain.clinic.dto.request.ClinicCreateRequest;
import api.docq.domain.clinic.dto.response.ClinicRespone;
import api.docq.domain.clinic.entity.Clinic;
import api.docq.domain.clinic.repository.ClinicRepository;
import api.docq.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ClinicService {
    private final ClinicRepository clinicRepository;
    private final UserService userService;

    @Transactional
    public ClinicRespone createClinic(Long userId, ClinicCreateRequest request) {
        userService.existsByUserId(userId);

        if (clinicRepository.existsByAddress(request.getAddress())) {
            throw new RuntimeException("이미 존재하는 병원입니다.");
        }

        validOpenAndCloseTime(request.getOpenTime(), request.getCloseTime());

        Clinic clinic = Clinic.of(
                request.getName(),
                request.getAddress(),
                request.getDepartMent(),
                request.getOpenTime(),
                request.getCloseTime()
        );

        clinicRepository.save(clinic);

        return ClinicRespone.of(
                clinic.getId(),
                clinic.getName(),
                clinic.getAddress(),
                clinic.getDepartMent(),
                clinic.getOpenTime(),
                clinic.getCloseTime(),
                clinic.isDeleted(),
                clinic.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public ClinicRespone findClinic(Long clinicId) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new RuntimeException("병원이 존재하지 않습니다."));

        return ClinicRespone.of(
                clinic.getId(),
                clinic.getName(),
                clinic.getAddress(),
                clinic.getDepartMent(),
                clinic.getOpenTime(),
                clinic.getCloseTime(),
                clinic.isDeleted(),
                clinic.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public Page<ClinicRespone> getClinic(Pageable pageable) {
        return clinicRepository.findAllIsNotDeleted(pageable)
                .map(clinic -> ClinicRespone.of(
                        clinic.getId(),
                        clinic.getName(),
                        clinic.getAddress(),
                        clinic.getDepartMent(),
                        clinic.getOpenTime(),
                        clinic.getCloseTime(),
                        clinic.isDeleted(),
                        clinic.getCreatedAt())
                );
    }

    private void validOpenAndCloseTime(LocalTime openTime, LocalTime closeTime) {
        if (!openTime.isBefore(closeTime)) {
            throw new RuntimeException("오픈 시간은 마감시간보다 빨라야 합니다.");
        }
    }
}
