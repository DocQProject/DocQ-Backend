package api.docq.domain.review.service;

import api.docq.common.dto.AuthUser;
import api.docq.common.image.entity.Image;
import api.docq.common.image.enums.ReferenceType;
import api.docq.common.image.repository.ImageRepository;
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


    private List<String> getImageUrls(Long reviewId) {
        List<Image> images = imageRepository.findByReferenceIdAndReferenceType(reviewId, ReferenceType.REVIEW);
        return images.stream()
                .map(Image::getImageUrl)
                .toList();
    }
}
