package api.docq.domain.search.service;

import api.docq.domain.clinic.entity.Clinic;
import api.docq.domain.clinic.enums.Department;
import api.docq.domain.clinic.repository.ClinicRepository;
import api.docq.domain.search.dto.response.ClinicSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClinicSearchService {

    private final ClinicRepository clinicRepository;

    @Transactional(readOnly = true)
    public List<ClinicSearchResponse> searchClinic(String query) {
        Department department = Department.fromName(query);
        List<Clinic> result = clinicRepository.searchByQuery(query, department);

        return result.stream()
                .map(clinic -> ClinicSearchResponse.of(
                        clinic.getId(),
                        clinic.getName(),
                        clinic.getAddress(),
                        clinic.getDepartment().name(),
                        clinic.getOpenTime(),
                        clinic.getCloseTime()
                )).toList();
    }


}
