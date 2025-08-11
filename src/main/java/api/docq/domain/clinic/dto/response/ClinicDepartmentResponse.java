package api.docq.domain.clinic.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ClinicDepartmentResponse {
    private final String name;
    private final String icon;
    
    @Builder
    private ClinicDepartmentResponse(String name, String icon) {
        this.name = name;
        this.icon = icon;
    }
    
    public static ClinicDepartmentResponse of(String name, String icon) {
        return ClinicDepartmentResponse.builder()
                .name(name)
                .icon(icon)
                .build();
    }
}
