package api.docq.domain.clinic.dto.response;

import api.docq.domain.clinic.enums.DepartMent;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
public class ClinicRespone {
    private final Long clinicId;

    private final String name;

    private final String address;

    private final DepartMent departMent;

    private final LocalTime openTime;

    private final LocalTime closeTime;

    private final boolean isDeleted;

    private final LocalDateTime createdAt;

    @Builder
    private ClinicRespone(Long clinicId, String name, String address, DepartMent departMent, LocalTime openTime, LocalTime closeTime, boolean isDeleted, LocalDateTime createdAt) {
        this.clinicId = clinicId;
        this.name = name;
        this.address = address;
        this.departMent = departMent;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.createdAt = createdAt;
        this.isDeleted = isDeleted;
    }

    public static ClinicRespone of(Long clinicId, String name, String address, DepartMent departMent, LocalTime openTime, LocalTime closeTime, boolean isDeleted, LocalDateTime createdAt) {
        return ClinicRespone.builder()
                .clinicId(clinicId)
                .name(name)
                .address(address)
                .departMent(departMent)
                .openTime(openTime)
                .closeTime(closeTime)
                .isDeleted(isDeleted)
                .createdAt(createdAt)
                .build();
    }
}
