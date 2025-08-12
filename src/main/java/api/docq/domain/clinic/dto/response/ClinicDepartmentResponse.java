package api.docq.domain.clinic.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ClinicDepartmentResponse {
    private final String name;

    @Builder
    private ClinicDepartmentResponse(String name) {
        this.name = name;
    }
    
    public static ClinicDepartmentResponse of(String name) {
        return ClinicDepartmentResponse.builder()
                .name(name)
                .build();
    }
}
