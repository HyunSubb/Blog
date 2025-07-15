package com.hyunsub.Blog.service;

import com.hyunsub.Blog.domain.Post;
import com.hyunsub.Blog.repository.PostRepository;
import com.hyunsub.Blog.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PostService {

    private final PostRepository postRepository;

    // @Autowired는 추천하지 않는 방식임.
    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void write(PostCreate postCreate) {
        // postCreate(dto) -> Post(entity)
        Post post = Post.builder()
                .title(postCreate.getTitle())
                .content(postCreate.getContent())
                .build();

        postRepository.save(post);
    }
}
