package api.docq.domain.comment.service;

import api.docq.common.dto.AuthUser;
import api.docq.domain.comment.dto.request.CommentRequest;
import api.docq.domain.comment.dto.response.CommentResponse;
import api.docq.domain.comment.entity.Comment;
import api.docq.domain.comment.repository.CommentRepository;
import api.docq.domain.post.entity.Post;
import api.docq.domain.user.entity.User;
import api.docq.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentResponse createComment(
            AuthUser authUser,
            CommentRequest request,
            Long postId
    ) {

        Comment comment = Comment.of(
                authUser.getUserId(),
                postId,
                request.getContent()
        );

        commentRepository.save(comment);

        return CommentResponse.of(
                authUser.getName(),
                request.getContent(),
                comment.getCreatedAt()
                );
    }

    @Transactional
    public CommentResponse updateComment(
            AuthUser authUser,
            Long commentId,
            CommentRequest request
    ) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException());

        validateAuthority(authUser, comment);

        comment.updateContent(request.getContent());

        return CommentResponse.of(
                authUser.getName(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }

    @Transactional
    public void deleteComment(AuthUser authUser, Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException());

        validateAuthority(authUser, comment);

        comment.deleteComment();
    }

    public List<Comment> findCommentsByPostId(Long postId) {
        return commentRepository.findCommentsByPostId(postId);
    }

    public List<CommentResponse> getCommentResponseList(Post post) {
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

    private void validateAuthority(AuthUser authUser, Comment comment) {
        boolean isAdmin = authUser.hasRole("ROLE_ADMIN");
        boolean isAuthor = comment.getUserId().equals(authUser.getUserId());

        if (!isAdmin && !isAuthor) {
            throw new RuntimeException();
        }
    }

}
