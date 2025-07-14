package com.hyunsub.Blog.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest // Mockmvc 주입
class PostControllerTest {

    @Autowired // Mockmvc 주입
    private MockMvc mockMvc;

    @Test // 테스트 메소드임을 나타냄
    @DisplayName("GET /post 요청 시 'Hello World'를 반환하는지 테스트") // 테스트 이름 설정
    void testPostEndpoint() throws Exception {
        // MockMvc를 사용하여 GET 요청을 보낸다.
        mockMvc.perform(get("/posts"))
                // HTTP 상태 코드가 200 OK인지 확인한다.
                .andExpect(status().isOk())
                // 응답 본문(content)이 "Hello World"인지 확인한다.
                .andExpect(content().string("Hello World"));
    }
}