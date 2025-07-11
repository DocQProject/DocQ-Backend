package api.docq.domain.review.service;

import api.docq.common.dto.AuthUser;
import api.docq.common.image.entity.Image;
import api.docq.common.image.enums.ReferenceType;
import api.docq.common.image.repository.ImageRepository;
import api.docq.common.image.service.ImageService;
import api.docq.domain.review.dto.request.ReviewRequest;
import api.docq.domain.review.dto.response.ReviewResponse;
import api.docq.domain.review.entity.Review;
import api.docq.domain.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                request.getStarPoint()
        );

        reviewRepository.save(review);

        List<String> imageUrls = getImageUrls(review.getId());

        return ReviewResponse.of(
                authUser.getName(),
                review.getContent(),
                review.getStarPoint(),
                imageUrls,
                review.getCreatedAt()
        ) ;
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
                review.getCreatedAt()
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

        for(String url : imageUrls) {
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
