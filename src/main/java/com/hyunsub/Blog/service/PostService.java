package com.hyunsub.Blog.service;

import com.hyunsub.Blog.domain.Post;
import com.hyunsub.Blog.repository.PostRepository;
import com.hyunsub.Blog.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Post get(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 글입니다."));

        return post;
    }
}
