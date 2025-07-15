package com.hyunsub.Blog.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * {
 *     "code": "400",
 *     "message": "잘못된 요청입니다."
 *     "validation" {
 *         "title": "값을 입력해주세요"
 *         ...
 *     }
 * }
 */
@Data
@Builder // 빌더 패턴 사용을 위해 추가
@NoArgsConstructor // 기본 생성자 (Jackson 직렬화를 위해 필요)
@AllArgsConstructor // 모든 필드를 포함하는 생성자
public class ErrorResponse {
    private String code; // final 제거하여 빌더 패턴 사용 용이하게 함

    private String message;

    private Map<String, String> validation; // 유효성 검사 오류 메시지를 담을 필드 추가
}
