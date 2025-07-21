package com.hyunsub.Blog.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component // AuthInterceptor를 스프링 빈으로 등록합니다.
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /*
            이 메서드는 컨트롤러의 핸들러(메서드)가 실행되기 전에 호출됩니다. 주로 인증, 권한 확인, 로깅, 특정 요청에 대한 사전 처리 등의 목적으로 사용됩니다.
            이 메서드가 true를 반환하면 다음 인터셉터나 컨트롤러로 요청 처리가 계속 진행됩니다. 만약 false를 반환하면 요청 처리가 중단되고,
            response 객체를 이용해 클라이언트에게 에러 페이지를 보여주거나 다른 페이지로 리다이렉트할 수 있습니다.
         */
        log.info(">> preHandle");
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        /*
            이 메서드는 컨트롤러의 핸들러가 실행된 후, 하지만 뷰가 렌더링되기 전에 호출됩니다.
            컨트롤러가 반환한 ModelAndView 객체에 접근하여 모델 데이터를 추가하거나 수정할 수 있습니다.
            예를 들어, 모든 페이지에 공통적으로 필요한 데이터를 모델에 추가하거나, 특정 조건에 따라 뷰 이름을 변경하는 등의 작업을 할 수 있습니다.
         */
        log.info(">> postHandle");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        /*
            이 메서드는 뷰 렌더링을 포함한 모든 요청 처리가 완료된 후에 호출됩니다.
            예외 발생 여부와 상관없이 항상 호출되므로, 주로 리소스 해제, 로깅, 성능 측정 등 사후 처리에 사용됩니다.
            ex 파라미터를 통해 요청 처리 중에 발생한 예외 정보를 얻을 수 있습니다.
         */
        log.info(">> afterCompletion");
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
