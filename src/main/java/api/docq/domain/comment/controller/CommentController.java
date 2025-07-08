package api.docq.domain.comment.controller;

import api.docq.common.dto.AuthUser;
import api.docq.domain.comment.dto.request.CommentRequest;
import api.docq.domain.comment.dto.response.CommentResponse;
import api.docq.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 댓글 작성
     */
    @PreAuthorize("hasAnyRole('USER', 'DOCTOR')")
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentResponse> createComment(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody CommentRequest request,
            @PathVariable Long postId
    ) {
        return ResponseEntity.ok(commentService.createComment(authUser, request, postId));
    }

    /**
     * 댓글 수정
     */
    @PreAuthorize("hasAnyRole('USER', 'DOCTOR')")
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody CommentRequest request
    ) {
        return ResponseEntity.ok(commentService.updateComment(authUser, commentId, request));
    }

    /**
     * 댓글 삭제
     */
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        commentService.deleteComment(authUser, commentId);
        return ResponseEntity.ok().build();
    }

}
