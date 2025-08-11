package api.docq.domain.clinic.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 치과, 안과, 이비인후과, 피부과
 */
@Getter
@RequiredArgsConstructor
public enum Department {
    DENTAL("치과", "https://img.icons8.com/?size=100&id=m0Jn3S6j3Tev&format=png&color=000000"),
    EYE_CLINIC("안과", "https://img.icons8.com/?size=100&id=SfoGooXDPPeC&format=png&color=000000"),
    ENT("이비인후과", "https://img.icons8.com/?size=100&id=23292&format=png&color=000000"),
    DERMATOLOGY("피부과", "https://img.icons8.com/?size=100&id=79381&format=png&color=000000");

    private final String departmentName;
    private final String departmentIcon;

    public static Department fromName(String name) {
        for (Department department : Department.values()) {
            if (department.departmentName.equals(name)) {
                return department;
            }
        }
        return null;
    }
}
