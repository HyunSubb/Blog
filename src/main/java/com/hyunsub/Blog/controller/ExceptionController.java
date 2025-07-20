package com.hyunsub.Blog.controller;

import com.hyunsub.Blog.exception.MyCustomException;
import com.hyunsub.Blog.exception.PostNotFound;
import com.hyunsub.Blog.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class ExceptionController {
    /**
     * 유효성 검사 실패 시 (예: @NotBlank, @NotNull, @Size 등) 발생하는 MethodArgumentNotValidException을 처리합니다.
     * HTTP Status 400 Bad Request와 함께 커스텀 ErrorResponse 형식으로 JSON을 반환합니다.
     */
    @ResponseBody // 메서드의 반환 값을 HTTP 응답 본문으로 직접 사용
    @ResponseStatus(HttpStatus.BAD_REQUEST) // HTTP 상태 코드를 400 Bad Request로 설정
    @ExceptionHandler(MethodArgumentNotValidException.class) // MethodArgumentNotValidException 발생 시 이 메소드를 실행
    public ErrorResponse invalidRequestHandler(MethodArgumentNotValidException e) { // 반환 타입을 ErrorResponse로 변경
        // 에러 정보를 담을 Map 생성
        Map<String, String> validationErrors = new HashMap<>();

        log.info(e.getMessage()); // 로깅은 디버깅에 유용합니다.

        // 모든 필드 에러를 순회하며 에러 메시지 추출
        e.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField(); // 에러가 발생한 필드 이름
            String errorMessage = error.getDefaultMessage();  // 해당 필드의 에러 메시지
            validationErrors.put(fieldName, errorMessage);
        });

        // ErrorResponse 객체를 빌더 패턴을 사용하여 생성하고 반환
        return ErrorResponse.builder()
                .code("400")
                .message("잘못된 요청입니다.")
                .validation(validationErrors)
                .build();
    }

    // 스프링에서 제공하는 예외는 위에처럼 모두 예외처리 시 하나하나마다 예외처리를 해주고 우리 커스텀 예외 같은 경우는
    // 최상위를 하나 만들어서 공통으로 처리하는 식으로 만든다.
    @ResponseBody
    @ExceptionHandler(MyCustomException.class)
    public ResponseEntity<ErrorResponse> MyCustomException(MyCustomException e) {

        int statusCode = e.getStatusCode();

        ErrorResponse body = ErrorResponse.builder()
                .code(String.valueOf(statusCode))
                .message(e.getMessage())
                .validation(e.getValidation())
                .build();

        ResponseEntity<ErrorResponse> responseEntity = ResponseEntity.status(statusCode).body(body);

        return responseEntity;

    }


}