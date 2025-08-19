package api.docq.domain.search.service;

import api.docq.domain.clinic.entity.Clinic;
import api.docq.domain.clinic.enums.Department;
import api.docq.domain.clinic.repository.ClinicRepository;
import api.docq.domain.review.repository.ReviewRepository;
import api.docq.domain.search.dto.response.ClinicSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicSearchService {

    private final ClinicRepository clinicRepository;
    private final ReviewRepository reviewRepository;

    @Transactional(readOnly = true)
    public Page<ClinicSearchResponse> searchClinic(String query, Pageable pageable) {
        Department department = Department.fromName(query);
        Page<Clinic> result = clinicRepository.searchByQuery(query, department, pageable);

        return result.map(clinic -> {
            Double averageStartPoint = reviewRepository.findAverageStarPointByClinicId(clinic.getId());

            return ClinicSearchResponse.of(
                    clinic.getId(),
                    clinic.getName(),
                    clinic.getAddress(),
                    clinic.getDepartment().name(),
                    clinic.getOpenTime(),
                    clinic.getCloseTime(),
                    averageStartPoint
            );
        });
    }

    @Transactional(readOnly = true)
    public List<ClinicSearchResponse> getClinicsByDepartment(Department department) {
        List<Clinic> result = clinicRepository.find5RandomClinics(department.name());

        return result.stream().map(clinic -> {
            Double averageStartPoint = reviewRepository.findAverageStarPointByClinicId(clinic.getId());

            return ClinicSearchResponse.of(
                    clinic.getId(),
                    clinic.getName(),
                    clinic.getAddress(),
                    clinic.getDepartment().name(),
                    clinic.getOpenTime(),
                    clinic.getCloseTime(),
                    averageStartPoint
            );
        }).toList();
    }


}
