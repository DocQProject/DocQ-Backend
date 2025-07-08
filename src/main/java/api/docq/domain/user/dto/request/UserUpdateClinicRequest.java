package api.docq.domain.user.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserUpdateClinicRequest {
    private final Long clinicId;
}
