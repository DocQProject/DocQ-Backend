package api.docq.domain.review.controller;

import api.docq.common.dto.AuthUser;
import api.docq.domain.review.dto.request.ReviewRequest;
import api.docq.domain.review.dto.response.ReviewResponse;
import api.docq.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clinics/{clinicId}")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/reviews")
    public ResponseEntity<ReviewResponse> createReview(
            @AuthenticationPrincipal AuthUser authUser,
            @RequestBody ReviewRequest request,
            @PathVariable Long clinicId
    ) {
        return ResponseEntity.ok(reviewService.createReview(authUser, request, clinicId));
    }
}
