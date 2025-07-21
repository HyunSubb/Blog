package com.hyunsub.Blog.config;

import com.hyunsub.Blog.config.data.UserSession;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthResolver implements HandlerMethodArgumentResolver {
    /*
        이 메서드는 현재 처리하려는 컨트롤러 메서드의 파라미터(MethodParameter)가 이 Resolver가 처리할 수 있는 타입인지 여부를 판단합니다.
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserSession.class);
    }

    /*
        컨트롤러에 들어온 파라미터 중 내가 위에서 설정한 클래스 (ex: UserSession.class) 인 경우에 이 메서드를 실행함.
        이런식으로 resolver를 활용해주면 특정 dto를 주입받은 컨트롤러가 특정 행동을 취할 수 있도록 할 수 있다.
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        UserSession userSession = new UserSession();
        userSession.setName("hyunsub");
        return userSession;
    }
}
