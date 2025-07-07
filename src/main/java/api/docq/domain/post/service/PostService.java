package api.docq.domain.post.service;

import api.docq.common.dto.AuthUser;
import api.docq.domain.comment.dto.response.CommentResponse;
import api.docq.domain.comment.entity.Comment;
import api.docq.domain.comment.repository.CommentRepository;
import api.docq.domain.post.dto.request.PostRequest;
import api.docq.domain.post.dto.response.PostListResponse;
import api.docq.domain.post.dto.response.PostResponse;
import api.docq.domain.post.entity.Post;
import api.docq.domain.post.repository.PostRepository;
import api.docq.domain.user.entity.User;
import api.docq.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public PostResponse createPost(AuthUser authUser, PostRequest request) {

        Post post = Post.of(
                authUser.getUserId(),
                request.getTitle(),
                authUser.getName(),
                request.getContent()
        );

        return PostResponse.of(
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getViewCount(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                Collections.emptyList()
        );
    }

    @Transactional
    public PostResponse findPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException());

        post.increaseViewCount();

        List<CommentResponse> comments = getCommentResponseList(post);

        return PostResponse.of(
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getViewCount(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                comments
        );
    }

    @Transactional(readOnly = true)
    public Page<PostListResponse> getPosts(Pageable pageable) {

        Page<Post> posts = postRepository.findAllNotDeleted(pageable);

        return posts.map(post -> PostListResponse.of(
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

        List<CommentResponse> comments = getCommentResponseList(post);

        return PostResponse.of(
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getViewCount(),
                post.getCreatedAt(),
                post.getUpdatedAt(),
                comments
        );
    }

    @Transactional
    public void deletePost(AuthUser authUser, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException());

        validateAuthority(authUser, post);

        post.deletePost();

        List<Comment> comments = commentRepository.findCommentsByPostId(post.getId());

        comments.forEach(Comment::deleteComment);
    }

    private void validateAuthority(AuthUser authUser, Post post) {
        boolean isAdmin = authUser.hasRole("ROLE_ADMIN");
        boolean isAuthor = post.getUserId().equals(authUser.getUserId());

        if (!isAdmin && !isAuthor) {
            throw new RuntimeException();
        }
    }

    private List<CommentResponse> getCommentResponseList(Post post) {
        List<Comment> commentList = commentRepository.findCommentsByPostId(post.getId());

        Set<Long> userIds = commentList.stream()
                .map(Comment::getUserId)
                .collect(Collectors.toSet());

        List<User> users = userRepository.findAllById(userIds);

        Map<Long, User> userMap = users.stream()
                .collect(Collectors.toMap(User::getId, Function.identity()));

        return commentList.stream()
                .map(comment -> {
                    User user = userMap.get(comment.getUserId());
                    return CommentResponse.of(
                            user.getName(),
                            comment.getContent(),
                            comment.getCreatedAt()
                    );
                }).toList();
    }

}
