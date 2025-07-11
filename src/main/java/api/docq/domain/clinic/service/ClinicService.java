package api.docq.domain.clinic.service;

import api.docq.domain.clinic.dto.request.ClinicCreateRequest;
import api.docq.domain.clinic.dto.response.ClinicCreateRespone;
import api.docq.domain.clinic.dto.response.ClinicGetAllResponse;
import api.docq.domain.clinic.dto.response.ClinicGetResponse;
import api.docq.domain.clinic.entity.Clinic;
import api.docq.domain.clinic.repository.ClinicRepository;
import api.docq.domain.review.dto.response.ReviewResponse;
import api.docq.domain.review.entity.Review;
import api.docq.domain.review.repository.ReviewRepository;
import api.docq.domain.review.service.ReviewService;
import api.docq.domain.user.entity.User;
import api.docq.domain.user.repository.UserRepository;
import api.docq.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClinicService {
    private final ClinicRepository clinicRepository;
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ReviewService reviewService;
    private final UserRepository userRepository;

    @Transactional
    public ClinicCreateRespone createClinic(Long userId, ClinicCreateRequest request) {
        userService.existsByUserId(userId);

        if (clinicRepository.existsByAddress(request.getAddress())) {
            throw new RuntimeException("이미 존재하는 병원입니다.");
        }

        validOpenAndCloseTime(request.getOpenTime(), request.getCloseTime());

        Clinic clinic = Clinic.of(
                request.getName(),
                request.getAddress(),
                request.getDepartment(),
                request.getOpenTime(),
                request.getCloseTime()
        );

        clinicRepository.save(clinic);

        return ClinicCreateRespone.of(
                clinic.getId(),
                clinic.getName(),
                clinic.getAddress(),
                clinic.getDepartment(),
                clinic.getOpenTime(),
                clinic.getCloseTime(),
                clinic.getCreatedAt()
        );
    }

    @Transactional(readOnly = true)
    public ClinicGetResponse findClinic(Long clinicId, Pageable pageable) {
        Clinic clinic = clinicRepository.findById(clinicId)
                .orElseThrow(() -> new RuntimeException("병원이 존재하지 않습니다."));

        Page<Review> reviews = reviewRepository.findAllByClinicId(clinicId, pageable);

        Set<Long> userIds = reviews.stream()
                .map(Review::getUserId)
                .collect(Collectors.toSet());

        Map<Long, User> users = userRepository.findAllById(userIds).stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        Page<ReviewResponse> reviewResponses = reviews.map(review -> {
            User user = users.get(review.getUserId());

            return ReviewResponse.of(
                    user != null ? user.getName() : "알 수 없음",  // null 체크는 방어적으로
                    review.getContent(),
                    review.getStarPoint(),
                    reviewService.getImageUrls(review.getId()),
                    review.getCreatedAt()
            );
        });

        return ClinicGetResponse.of(
                clinic.getId(),
                clinic.getName(),
                clinic.getAddress(),
                clinic.getDepartment(),
                clinic.getOpenTime(),
                clinic.getCloseTime(),
                clinic.getCreatedAt(),
                reviewResponses
        );
    }

    @Transactional(readOnly = true)
    public Page<ClinicGetAllResponse> getClinic(Pageable pageable) {
        return clinicRepository.findAllIsNotDeleted(pageable)
                .map(clinic -> ClinicGetAllResponse.of(
                        clinic.getId(),
                        clinic.getName(),
                        clinic.getAddress(),
                        clinic.getDepartment(),
                        clinic.getOpenTime(),
                        clinic.getCloseTime(),
                        clinic.getCreatedAt())
                );
    }

    private void validOpenAndCloseTime(LocalTime openTime, LocalTime closeTime) {
        if (!openTime.isBefore(closeTime)) {
            throw new RuntimeException("오픈 시간은 마감시간보다 빨라야 합니다.");
        }
    }
}
