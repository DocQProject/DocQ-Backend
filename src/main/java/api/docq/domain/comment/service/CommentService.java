package api.docq.domain.comment.service;

import api.docq.common.dto.AuthUser;
import api.docq.domain.comment.dto.request.CommentRequest;
import api.docq.domain.comment.dto.response.CommentResponse;
import api.docq.domain.comment.entity.Comment;
import api.docq.domain.comment.repository.CommentRepository;
import api.docq.domain.user.entity.User;
import api.docq.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long postId) {
        return commentRepository.findCommentsByPostId(postId).stream()
                .map(comment ->
                        {
                            User user = userRepository.findById(comment.getUserId())
                                    .orElseThrow(() -> new RuntimeException());
                            return CommentResponse.of(
                                    user.getName(),
                                    comment.getContent(),
                                    comment.getCreatedAt()
                                    );
                        }
                ).toList();
    }

    @Transactional
    public CommentResponse updateComment(
            AuthUser authUser,
            Long commentId,
            CommentRequest request
    ) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException());

        User user = userRepository.findById(authUser.getUserId())
                .orElseThrow(() -> new RuntimeException());

        boolean isAdmin = authUser.hasRole("ROLE_ADMIN");
        boolean isAuthor = comment.getUserId().equals(authUser.getUserId());

        if (!isAdmin && !isAuthor) {
            throw new RuntimeException();
        }
        comment.updateContent(request.getContent());

        return CommentResponse.of(
                user.getName(),
                comment.getContent(),
                comment.getCreatedAt()
        );
    }

    @Transactional
    public void deleteComment(AuthUser authUser, Long commentId) {

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException());

        boolean isAdmin = authUser.hasRole("ROLE_ADMIN");
        boolean isAuthor = comment.getUserId().equals(authUser.getUserId());

        if (!isAdmin && !isAuthor) {
            throw new RuntimeException();
        }

        comment.deleteComment();
    }
}
