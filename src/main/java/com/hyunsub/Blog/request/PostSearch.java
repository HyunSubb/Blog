package com.hyunsub.Blog.request;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSearch {
    // @Builder.Default는 빌더 패턴 사용 시 기본값을 설정해준다.
    // page는 0부터 시작한다. (Spring Data JPA 기본). 1부터 시작하게 하려면 -1 조정 필요.
    @Builder.Default
    @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
    private Integer page = 1;

    @Builder.Default
    private Integer size = 5;
}
