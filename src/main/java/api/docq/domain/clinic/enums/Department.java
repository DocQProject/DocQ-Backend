package api.docq.domain.clinic.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 치과, 안과, 이비인후과, 피부과
 */
@Getter
@RequiredArgsConstructor
public enum Department {
    DENTAL("치과"),
    EYE_CLINIC("안과"),
    ENT("이비인후과"),
    DERMATOLOGY("피부과");

    private final String departmentName;
}
