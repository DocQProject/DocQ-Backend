package api.docq.domain.search.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalTime;

@Getter
public class ClinicSearchResponse {

    private final Long clinicId;

    private final String name;

    private final String address;

    private final String department;

    private final String openTime;

    private final String closeTime;

    private final Integer starPoint;

    @Builder
    private ClinicSearchResponse(Long clinicId, String name, String address, String department, String openTime, String closeTime, Integer starPoint) {
        this.clinicId = clinicId;
        this.name = name;
        this.address = address;
        this.department = department;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.starPoint = starPoint;
    }

    public static ClinicSearchResponse of(Long clinicId, String name, String address, String department, String openTime, String closeTime, Integer starPoint) {
        return ClinicSearchResponse.builder()
                .clinicId(clinicId)
                .name(name)
                .address(address)
                .department(department)
                .openTime(openTime)
                .closeTime(closeTime)
                .starPoint(starPoint)
                .build();
    }
}
