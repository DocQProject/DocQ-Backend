package api.docq.domain.review.service;

import api.docq.common.dto.AuthUser;
import api.docq.common.image.entity.Image;
import api.docq.common.image.enums.ReferenceType;
import api.docq.common.image.repository.ImageRepository;
import api.docq.common.image.service.ImageService;
import api.docq.domain.clinic.entity.Clinic;
import api.docq.domain.clinic.repository.ClinicRepository;
import api.docq.domain.review.dto.request.ReviewRequest;
import api.docq.domain.review.dto.response.ReviewResponse;
import api.docq.domain.review.entity.Review;
import api.docq.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;

    @Transactional
    public ReviewResponse createReview(AuthUser authUser, ReviewRequest request, Long clinicId) {
        Review review = Review.of(
                authUser.getUserId(),
                clinicId,
                request.getContent(),
                authUser.getName(),
                request.getStarPoint()
        );

        reviewRepository.save(review);

        List<String> imageUrls = getImageUrls(review.getId());

        return ReviewResponse.of(
                authUser.getName(),
                review.getContent(),
                review.getStarPoint(),
                imageUrls,
                review.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }

    @Transactional(readOnly = true)
    public Page<ReviewResponse> getReviews(Long clinicId, Pageable pageable) {
        Page<Review> reviews = reviewRepository.findAllByClinicId(clinicId, pageable);

        return reviews
                .map(review -> {

                    List<String> imageUrls = getImageUrls(review.getId());

                    return ReviewResponse.of(
                            review.getAuthor(),
                            review.getContent(),
                            review.getStarPoint(),
                            imageUrls,
                            review.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
                    );
                });
    }

    @Transactional
    public ReviewResponse updateReview(Long userId, String userName, ReviewRequest request, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰가 존재하지 않습니다."));

        if (!userId.equals(review.getUserId())) {
            throw new RuntimeException("해당 리뷰를 작성한 사용자가 아닙니다.");
        }

        review.updateContentAndStar(request.getContent(), request.getStarPoint());
        List<String> imageUrls = getImageUrls(review.getId());

        return ReviewResponse.of(
                userName,
                review.getContent(),
                review.getStarPoint(),
                imageUrls,
                review.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }

    @Transactional
    public void deleteReview(Long userId, Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new RuntimeException("리뷰가 존재하지 않습니다."));

        if (!userId.equals(review.getUserId())) {
            throw new RuntimeException("해당 리뷰를 작성한 사용자가 아닙니다.");
        }

        List<String> imageUrls = getImageUrls(reviewId);

        for (String url : imageUrls) {
            imageService.deleteImage(url);
        }

        reviewRepository.delete(review);
    }

    public List<String> getImageUrls(Long reviewId) {
        List<Image> images = imageRepository.findByReferenceIdAndReferenceType(reviewId, ReferenceType.REVIEW);
        return images.stream()
                .map(Image::getImageUrl)
                .toList();
    }
}
