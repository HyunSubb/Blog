package com.hyunsub.Blog.controller;

import com.hyunsub.Blog.domain.Post;
import com.hyunsub.Blog.request.PostCreate;
import com.hyunsub.Blog.request.PostEdit;
import com.hyunsub.Blog.request.PostSearch;
import com.hyunsub.Blog.response.PostResponse;
import com.hyunsub.Blog.service.PostService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
public class PostController {

    PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 글 등록 -> POST Method
    @PostMapping("/posts")
    public ResponseEntity<?> post(@RequestBody @Valid PostCreate request) {
        // 데이터를 검증하는 이유
        // 1. client 개발자가 깜박할 수 있다. 실수로 값을 안보낼 수 있음.
        // 2. clinet bug로 값이 누락될 수 있다.
        // 3. 외부에 나쁜 사람이 값을 임의로 조작해서 보낼 수 있다.
        // 4. DB에 값을 저장할 때 의도치 않은 오류가 발생할 수 있다.
        // 5. 서버 개발자의 편안함을 위해서
        // 검증과정을 거치고 나서야 DB에 저장될 수가 있는거임.

        request.validate(); // 예외 처리를 이런식으로도 가능하다.

        Long savedPostId = postService.write(request);

        Map<String, Long> response = new HashMap<>();
        response.put("postId", savedPostId);
        // Case 1. 저장한 데이터 Entity로 응답하기
        // Case 2. 저장한 데이터의 id를 응답하기
        // Case 3. 응답 필요 없음.
        // Bad Case. 서버에서 반드시 이렇게 할거다! (X) -> 서버에서 유연하게 대응하는 게 좋다.
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 단건 조회 API (1개의 글 Post를 가져오는 기능)
    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> get(@PathVariable Long postId) {
        PostResponse postResponse = postService.get(postId);
        return new ResponseEntity<>(postResponse, HttpStatus.OK);
    }

    // 복수 조회 API (여러 개의 글 post를 가져오는 기능)
    // 내가 GET /posts와 같은 조회 요청에서 page와 size 정보를 보내려면, URL 쿼리 파라미터(GET 요청의 표준)를 사용해야 한다.
    // 나는 json으로 보내고 계속 뭔가 안되서 오류인줄 알았는데 내가 잘못한 거 였다.
    @GetMapping("/posts")
    public ResponseEntity<?> getList(@Valid PostSearch postSearch) {
        List<PostResponse> postResponses = postService.getList(postSearch);
        return new ResponseEntity<>(postResponses, HttpStatus.OK);
    }

    // 글 수정 API
    @PatchMapping("/posts/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit postEdit) {
        log.info(postId + "/" + postEdit);
        postService.edit(postId, postEdit);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId) {
        postService.delete(postId);
    }

}
