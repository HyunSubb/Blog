package com.hyunsub.Blog.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostCreate {
//    NotBlank는 이 Dto를 받을 컨트롤러에 @Valid 어노테이션을 달아줘야 유효한 기능을 한다.

    @NotBlank(message = "제목을 입력해주세요.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}

