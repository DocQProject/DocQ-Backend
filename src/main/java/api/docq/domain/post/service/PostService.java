package api.docq.domain.post.service;

import api.docq.common.dto.AuthUser;
import api.docq.domain.post.dto.request.PostRequest;
import api.docq.domain.post.dto.response.PostResponse;
import api.docq.domain.post.entity.Post;
import api.docq.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
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

        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .author(authUser.getName())
                .viewCount(post.getViewCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    @Transactional
    public PostResponse getPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException());

        post.increaseViewCount();

        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .viewCount(post.getViewCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    @Transactional
    public PostResponse updatePost(AuthUser authUser, Long postId, PostRequest request) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException());

        // Post 작성자의 ID와 Post 를 수정하려는 클라이언트의 ID 일치 확인
        if (post.getUserId().equals(authUser.getUserId())) {
            post.updatePost(request.getTitle(), request.getContent());
        } else {
            throw new RuntimeException();
        }

        return PostResponse.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .author(post.getAuthor())
                .viewCount(post.getViewCount())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    @Transactional
    public void deletePost(AuthUser authUser, Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException());

        // Post 작성자의 ID와 Post 를 삭제하려는 클라이언트의 ID 일치 확인
        if (post.getUserId().equals(authUser.getUserId())) {
            post.deletePost();
        }else {
            throw new RuntimeException();
        }
    }


}
