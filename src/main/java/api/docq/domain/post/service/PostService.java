package api.docq.domain.post.service;

import api.docq.common.dto.AuthUser;
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

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    @Transactional
    public PostResponse createPost(AuthUser authUser, PostRequest request) {

        Post post = Post.of(
                authUser.getUserId(),
                request.getTitle(),
                authUser.getName(),
                request.getContent()
        );

        postRepository.save(post);

        return PostResponse.of(
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getViewCount(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    @Transactional
    public PostResponse findPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException());

        post.increaseViewCount();

        return PostResponse.of(
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getViewCount(),
                post.getCreatedAt(),
                post.getUpdatedAt()
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

        // Post 작성자의 ID와 Post 를 수정하려는 클라이언트의 ID 일치 확인
        if (!post.getUserId().equals(authUser.getUserId())) {
            throw new RuntimeException();
        }
        post.updatePost(request.getTitle(), request.getContent());

        return PostResponse.of(
                post.getTitle(),
                post.getContent(),
                post.getAuthor(),
                post.getViewCount(),
                post.getCreatedAt(),
                post.getUpdatedAt()
        );
    }

    @Transactional
    public void deletePost(AuthUser authUser, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException());

        boolean isAdmin = authUser.hasRole("ROLE_ADMIN");
        boolean isAuthor = post.getUserId().equals(authUser.getUserId());

        // ADMIN 이거나 해당 게시글 작성자이면 삭제
        if (!isAdmin && !isAuthor) {
            throw new RuntimeException();
        }

        post.deletePost();
    }


}
