package api.docq.domain.clinic.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ClinicGetDepartmentResponse {
    private List<ClinicDepartmentResponse> departments;

    @Builder
    private ClinicGetDepartmentResponse(List<ClinicDepartmentResponse> departments) {
        this.departments = departments;
    }

    public static ClinicGetDepartmentResponse of (List<ClinicDepartmentResponse> departments) {
        return ClinicGetDepartmentResponse.builder()
                .departments(departments)
                .build();
    }
}
