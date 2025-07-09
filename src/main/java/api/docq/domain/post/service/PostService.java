package api.docq.domain.post.service;

import api.docq.common.dto.AuthUser;
import api.docq.common.image.entity.Image;
import api.docq.common.image.enums.ReferenceType;
import api.docq.common.image.repository.ImageRepository;
import api.docq.domain.comment.dto.response.CommentResponse;
import api.docq.domain.comment.entity.Comment;
import api.docq.domain.comment.service.CommentService;
import api.docq.domain.post.dto.request.PostRequest;
import api.docq.domain.post.dto.response.PostListResponse;
import api.docq.domain.post.dto.response.PostResponse;
import api.docq.domain.post.entity.Post;
import api.docq.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentService commentService;
    private final ImageRepository imageRepository;

    @Transactional
    public PostResponse createPost(AuthUser authUser, PostRequest request) {

        Post post = Post.of(
                authUser.getUserId(),
                request.getTitle(),
                authUser.getName(),
                request.getContent()
        );

        List<String> imageURLs = getImageUrls(post);


        return PostResponse.of(
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getViewCount(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                Collections.emptyList(),
                imageURLs
                );
    }

    @Transactional
    public PostResponse findPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException());

        post.increaseViewCount();

        List<CommentResponse> comments = commentService.getCommentResponseList(post);

        List<String> imageURLs = getImageUrls(post);

        return PostResponse.of(
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getViewCount(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                comments,
                imageURLs
        );
    }


    @Transactional(readOnly = true)
    public Page<PostListResponse> getPosts(Pageable pageable) {

        Page<Post> posts = postRepository.findAllNotDeleted(pageable);

        return posts.map(
                post -> PostListResponse.of(
                post.getTitle(),
                post.getAuthor(),
                post.getViewCount(),
                post.getCreatedAt()
        ));
    }


    @Transactional
    public PostResponse updatePost(AuthUser authUser, Long postId, PostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException());

        validateAuthority(authUser, post);

        post.updatePost(request.getTitle(), request.getContent());

        List<CommentResponse> comments = commentService.getCommentResponseList(post);

        List<String> imageURLs = getImageUrls(post);

        return PostResponse.of(
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getViewCount(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                comments,
                imageURLs
        );
    }

    @Transactional
    public void deletePost(AuthUser authUser, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException());

        validateAuthority(authUser, post);

        post.deletePost();

        List<Comment> comments = commentService.findCommentsByPostId(post.getId());

        comments.forEach(Comment::deleteComment);
    }

    private void validateAuthority(AuthUser authUser, Post post) {
        boolean isAdmin = authUser.hasRole("ROLE_ADMIN");
        boolean isAuthor = post.getUserId().equals(authUser.getUserId());

        if (!isAdmin && !isAuthor) {
            throw new RuntimeException();
        }
    }

    private List<String> getImageUrls(Post post) {
        List<Image> images = imageRepository.findByReferenceIdAndReferenceType(post.getId(), ReferenceType.POST);
        return images.stream()
                .map(Image::getImageUrl)
                .toList();
    }

}
