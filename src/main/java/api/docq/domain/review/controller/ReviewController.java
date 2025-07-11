package api.docq.domain.review.controller;

import api.docq.common.dto.AuthUser;
import api.docq.domain.review.dto.request.ReviewRequest;
import api.docq.domain.review.dto.response.ReviewResponse;
import api.docq.domain.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReviewController {

    private final ReviewService reviewService;

    /**
     *  리뷰 작성
     */
    @PostMapping("/clinics/{clinicId}/reviews")
    public ResponseEntity<ReviewResponse> createReview(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody ReviewRequest request,
            @PathVariable Long clinicId
    ) {
        return ResponseEntity.ok(reviewService.createReview(authUser, request, clinicId));
    }

    /**
     *  리뷰 수정
     */
    @PutMapping("/reviews/{reviewId}")
    public ResponseEntity<ReviewResponse> updateReview(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody ReviewRequest request,
            @PathVariable Long reviewId
    ) {
        return ResponseEntity.ok(reviewService.updateReview(authUser.getUserId(), authUser.getName(), request, reviewId));
    }

    /**
     *  리뷰 삭제
     */
    @DeleteMapping("/reviews/{reviewId}")
    public ResponseEntity<Void> deleteReview(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long reviewId

    ) {
        reviewService.deleteReview(authUser.getUserId(), reviewId);
        return ResponseEntity.ok().build();
    }
}
