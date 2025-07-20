package com.hyunsub.Blog.request;


import com.hyunsub.Blog.exception.InvalidRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
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

    public void validate() {
        if(title.contains("바보")) {
            throw new InvalidRequest("title", "바보는 제목에 사용하지 못해요.");
        }
    }

}

