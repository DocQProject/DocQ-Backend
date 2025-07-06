package api.docq.domain.post.controller;

import api.docq.common.dto.AuthUser;
import api.docq.domain.post.dto.request.PostRequest;
import api.docq.domain.post.dto.response.PostListResponse;
import api.docq.domain.post.dto.response.PostResponse;
import api.docq.domain.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    /**
     * 게시글 작성
     */
    @PreAuthorize("hasAnyRole('USER', 'DOCTOR')")
    @PostMapping
    public ResponseEntity<PostResponse> createPost(
            @AuthenticationPrincipal AuthUser authUser,
            @Valid @RequestBody PostRequest request
    ) {
        return ResponseEntity.ok(postService.createPost(authUser, request));
    }

    /**
     * 게시글 단건 조회
     */
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> findPost(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.findPost(postId));
    }

    /**
     * 게시글 다건 조회
     */
    @GetMapping
    public ResponseEntity<Page<PostListResponse>> getPosts(Pageable pageable) {
        return ResponseEntity.ok(postService.getPosts(pageable));
    }

    /**
     * 게시글 수정
     */
    @PreAuthorize("hasAnyRole('USER', 'DOCTOR')")
    @PutMapping("/{postId}")
    public ResponseEntity<PostResponse> updatePost(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long postId,
            @Valid @RequestBody PostRequest request) {
        return ResponseEntity.ok(postService.updatePost(authUser, postId, request));
    }

    /**
     * 게시글 삭제
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long postId) {
        postService.deletePost(authUser, postId);
        return ResponseEntity.ok().build();
    }
}
