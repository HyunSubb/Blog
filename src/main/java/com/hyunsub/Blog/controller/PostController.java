package com.hyunsub.Blog.controller;

import com.hyunsub.Blog.domain.Post;
import com.hyunsub.Blog.request.PostCreate;
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

        Long savedPostId = postService.write(request);

        Map<String, Long> response = new HashMap<>();
        response.put("postId", savedPostId);
        // Case 1. 저장한 데이터 Entity로 응답하기
        // Case 2. 저장한 데이터의 id를 응답하기
        // Case 3. 응답 필요 없음.
        // Bad Case. 서버에서 반드시 이렇게 할거다! (X) -> 서버에서 유연하게 대응하는 게 좋다.
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<Post> get(@PathVariable Long postId) {
        Post post = postService.get(postId);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }
}
