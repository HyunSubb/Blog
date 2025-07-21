package com.hyunsub.Blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor; // AuthInterceptor를 주입받을 준비

    // 생성자를 통한 의존성 주입 (Spring 4.3 이상 권장)
    public WebMvcConfig(AuthInterceptor authInterceptor) {
        this.authInterceptor = authInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // AuthInterceptor를 등록합니다.
        registry.addInterceptor(authInterceptor)
                // 인터셉터를 적용할 URL 패턴을 지정합니다.
                // 예를 들어, 모든 경로에 적용하려면 "/**"
                // 특정 경로에만 적용하려면 "/admin/**", "/users/**" 등
                .addPathPatterns("/**") // 예시: 모든 요청에 적용

                // 인터셉터에서 제외할 URL 패턴을 지정합니다.
                // 로그인, 회원가입 페이지 등 인증이 필요 없는 페이지는 제외할 수 있습니다.
                .excludePathPatterns("/login", "/register", "/css/**", "/js/**", "/images/**");
        // .order(1); // 여러 인터셉터가 있을 경우 순서를 지정할 수 있습니다. (낮은 숫자가 먼저 실행)
    }
}
