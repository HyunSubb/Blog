package com.hyunsub.Blog.service;

import com.hyunsub.Blog.domain.Post;
import com.hyunsub.Blog.exception.PostNotFound;
import com.hyunsub.Blog.repository.PostRepository;
import com.hyunsub.Blog.request.PostCreate;
import com.hyunsub.Blog.request.PostEdit;
import com.hyunsub.Blog.request.PostSearch;
import com.hyunsub.Blog.response.PostResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
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
                .orElseThrow(()-> new PostNotFound());

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
    public Page<PostResponse> getList(PostSearch postSearch) {
        // 글이 너무 많은 경우 -> 비용이 너무 많이 든다.
        // 글이 100,000,000 -> DB 글 모두를 조회하는 경우 -> DB가 뻗을 수 있다.
        // DB -> 애플리케이션 서버로 전달하는 시간, 트래픽 비용 등이 많이 발생할 수 있다.
        // 그래서 게시글을 모두 조회하는 경우는 없음 -> 페이징 처리를 해야 됨. -> findAll()에는 Pagealbe 객체를 넘길 수 있음.

        // Pageable 객체 생성: 페이지 번호 (0-based), 페이지 크기, 정렬 기준
        // Spring Data JPA의 Pageable은 0부터 시작하므로, 클라이언트가 1페이지를 요청하면 page - 1을 합니다.
        Pageable pageable = PageRequest.of(
                postSearch.getPage()-1, // 페이지 번호 (클라이언트 요청은 1부터 시작, JPA는 0부터 시작)
                postSearch.getSize(),     // 페이지 크기
                Sort.by(Sort.Direction.DESC, "id") // ID 기준으로 내림차순 정렬 (최신 글이 먼저 오도록)
        );

        // QueryDSL로 구현된 커스텀 메서드 호출
        Page<Post> postPage = postRepository.getPostPageBySearchCondition(postSearch, pageable);

//        return postPage.getContent().stream()
//                .map(post -> PostResponse.builder()
//                        .id(post.getId())
//                        .title(post.getTitle())
//                        .content(post.getContent())
//                        .build())
//                .collect(Collectors.toList());

        // Page<Post>를 Page<PostResponse>로 매핑합니다.
        // Page 인터페이스의 map() 메서드는 내부 콘텐츠(List<Post>)를 변환(Post -> PostResponse)하면서
        // 페이징 정보(총 요소 수, 총 페이지 수 등)를 그대로 유지하여 새로운 Page<PostResponse>를 반환합니다.
        return postPage.map(post -> PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build());

//        // 일반 JPA DATA로 구현한 메서드 호출
//        return postRepository.findAll(pageable).stream() // 1. 모든 Post 엔티티를 조회하고 스트림 생성
//                .map(post -> PostResponse.builder() // 2. 각 Post 엔티티를 PostResponse DTO로 변환
//                        .id(post.getId())
//                        .title(post.getTitle())
//                        .content(post.getContent())
//                        .build())
//                .collect(Collectors.toList()); // 3. 변환된 DTO들을 리스트로 수집
    }


    public void edit(Long postId, PostEdit postEdit) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFound());

        String title = postEdit.getTitle() != null ? postEdit.getTitle() : post.getTitle();
        String content = postEdit.getContent() != null ? postEdit.getContent() : post.getContent();

        log.info("Updating Post with ID: {} - Old Title: {}, New Title: {} | Old Content: {}, New Content: {}",
                 postId, post.getTitle(), title, post.getContent(), content);
        post.update(title, content);
        log.info("Post updated in memory. Title: {}, Content: {}", post.getTitle(), post.getContent());
    }

    public void delete(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostNotFound());

        // -> 존재하는 경우
        postRepository.delete(post);
    }
}
