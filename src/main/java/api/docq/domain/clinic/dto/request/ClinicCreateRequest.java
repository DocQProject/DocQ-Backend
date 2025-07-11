package api.docq.domain.clinic.dto.request;

import api.docq.domain.clinic.enums.Department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class ClinicCreateRequest {
    @NotBlank(message = "병원 이름을 입력해주세요.")
    private final String name;

    @NotBlank(message = "주소를 입력해주세요.")
    private final String address;

    @NotNull(message = "진료과를 입력해주세요.")
    private final Department department;

    private final LocalTime openTime;

    private final LocalTime closeTime;
}
