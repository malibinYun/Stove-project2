package com.example.usermanagement.controller.resolver;

import com.example.usermanagement.controller.resolver.annotation.AdminUser;
import com.example.usermanagement.controller.resolver.annotation.RequestUser;
import com.example.usermanagement.domain.entity.Account;
import com.example.usermanagement.exception.NoPermissionException;
import com.example.usermanagement.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;


@Component
@RequiredArgsConstructor
public class AccountArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String AUTHORIZATION = "Authorization";

    private final AccountService accountService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(RequestUser.class)
                || methodParameter.hasParameterAnnotation(AdminUser.class);
    }

    @Override
    public Object resolveArgument(
            MethodParameter methodParameter,
            ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest,
            WebDataBinderFactory webDataBinderFactory
    ) throws Exception {
        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        String token = request.getHeader(AUTHORIZATION);
        if (token == null) {
            throw new IllegalArgumentException("헤더에 토큰이 없습니다.");
        }
        Account account = accountService.getAccountByToken(token);
        validateAdmin(methodParameter, account);
        return account;
    }

    private void validateAdmin(MethodParameter methodParameter, Account account) {
        if (methodParameter.hasParameterAnnotation(AdminUser.class) && account.isNotAdmin()) {
            throw new NoPermissionException();
        }
    }
}
