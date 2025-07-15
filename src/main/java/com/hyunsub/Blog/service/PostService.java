package com.hyunsub.Blog.service;

import com.hyunsub.Blog.domain.Post;
import com.hyunsub.Blog.repository.PostRepository;
import com.hyunsub.Blog.request.PostCreate;
import com.hyunsub.Blog.response.PostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PostService {

    private final PostRepository postRepository;

    // @Autowired는 추천하지 않는 방식임.
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Long write(PostCreate postCreate) {
        // postCreate(dto) -> Post(entity)
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        Post savedPost = postRepository.save(post);

        return savedPost.getId();
    }

    public PostResponse get(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 글입니다."));

        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        return postResponse;
    }

    /**
     * (stream(), map(), collect())
     * 1. 데이터베이스에서 Post 테이블의 모든 데이터를 가져옵니다. ( postRepository.findAll() )
     *
     * 2. 가져온 Post 엔티티 리스트를 스트림으로 만듭니다. ( .stream() )
     *
     * 3. 스트림의 각 Post 엔티티를 순회하면서, 각각을 PostResponse DTO 객체로 변환합니다. ( .map(...) )
     *
     * 4. 모든 변환이 끝나면, 변환된 PostResponse DTO들을 하나의 List<PostResponse>로 묶어서 반환합니다. ( .collect(Collectors.toList()) )
     */
    public List<PostResponse> getList() {
        return postRepository.findAll().stream() // 1. 모든 Post 엔티티를 조회하고 스트림 생성
                .map(post -> PostResponse.builder() // 2. 각 Post 엔티티를 PostResponse DTO로 변환
                        .id(post.getId())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .build())
                .collect(Collectors.toList()); // 3. 변환된 DTO들을 리스트로 수집
    }
}
