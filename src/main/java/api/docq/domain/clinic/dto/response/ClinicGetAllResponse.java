package api.docq.domain.clinic.dto.response;

import api.docq.domain.clinic.enums.Department;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class ClinicGetAllResponse {
    private final Long clinicId;

    private final String name;

    private final String address;

    private final Department department;

    private final LocalTime openTime;

    private final LocalTime closeTime;

    @Builder
    private ClinicGetAllResponse(Long clinicId, String name, String address, Department department, LocalTime openTime, LocalTime closeTime) {
        this.clinicId = clinicId;
        this.name = name;
        this.address = address;
        this.department = department;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public static ClinicGetAllResponse of(Long clinicId, String name, String address, Department department, LocalTime openTime, LocalTime closeTime) {
        return ClinicGetAllResponse.builder()
                .clinicId(clinicId)
                .name(name)
                .address(address)
                .department(department)
                .openTime(openTime)
                .closeTime(closeTime)
                .build();
    }
}
