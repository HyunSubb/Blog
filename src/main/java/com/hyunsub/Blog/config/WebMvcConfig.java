package com.hyunsub.Blog.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    private final AuthInterceptor authInterceptor; // AuthInterceptor를 주입받을 준비
    private final AuthResolver authResolver;

    // 생성자를 통한 의존성 주입 (Spring 4.3 이상 권장)
    public WebMvcConfig(AuthInterceptor authInterceptor, AuthResolver authResolver) {
        this.authInterceptor = authInterceptor;
        this.authResolver = authResolver;
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        // AuthInterceptor를 등록합니다.
//        registry.addInterceptor(authInterceptor)
//                // 인터셉터를 적용할 URL 패턴을 지정합니다.
//                // 예를 들어, 모든 경로에 적용하려면 "/**"
//                // 특정 경로에만 적용하려면 "/admin/**", "/users/**" 등
//                .addPathPatterns("/**"); // 예시: 모든 요청에 적용
//    }

    /*
        스프링 MVC의 모든 컨트롤러에서 모든 메서드 파라미터를 처리할 때 해당 내가 추가한 resolver를 사용한다.
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authResolver);
    }
}
