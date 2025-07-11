package api.docq.domain.clinic.dto.response;

import api.docq.domain.clinic.enums.Department;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class ClinicCreateRespone {
    private final Long clinicId;

    private final String name;

    private final String address;

    private final Department department;

    private final LocalTime openTime;

    private final LocalTime closeTime;

    private final LocalDateTime createdAt;

    @Builder
    private ClinicCreateRespone(Long clinicId, String name, String address, Department department, LocalTime openTime, LocalTime closeTime, LocalDateTime createdAt) {
        this.clinicId = clinicId;
        this.name = name;
        this.address = address;
        this.department = department;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.createdAt = createdAt;
    }

    public static ClinicCreateRespone of(Long clinicId, String name, String address, Department department, LocalTime openTime, LocalTime closeTime, LocalDateTime createdAt) {
        return ClinicCreateRespone.builder()
                .clinicId(clinicId)
                .name(name)
                .address(address)
                .department(department)
                .openTime(openTime)
                .closeTime(closeTime)
                .createdAt(createdAt)
                .build();
    }
}
