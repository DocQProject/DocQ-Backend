package api.docq.domain.clinic.dto.response;

import api.docq.domain.clinic.enums.Department;
import api.docq.domain.review.dto.response.ReviewResponse;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class ClinicOwnerGetResponse {
    private final Long clinicId;

    private final String name;

    private final String address;

    private final Department department;

    private final String openTime;

    private final String closeTime;

    private final String createdAt;

    private final Page<ReviewResponse> reviews;

    @Builder
    private ClinicOwnerGetResponse(Long clinicId, String name, String address, Department department, String openTime, String closeTime, String createdAt, Page<ReviewResponse> reviews) {
        this.clinicId = clinicId;
        this.name = name;
        this.address = address;
        this.department = department;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.createdAt = createdAt;
        this.reviews =reviews;
    }

    public static ClinicOwnerGetResponse of(Long clinicId, String name, String address, Department department, String openTime, String closeTime, String createdAt, Page<ReviewResponse> reviews) {
        return ClinicOwnerGetResponse.builder()
                .clinicId(clinicId)
                .name(name)
                .address(address)
                .department(department)
                .openTime(openTime)
                .closeTime(closeTime)
                .createdAt(createdAt)
                .reviews(reviews)
                .build();
    }
}
