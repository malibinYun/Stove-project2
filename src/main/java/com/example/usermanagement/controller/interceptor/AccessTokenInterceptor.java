//package com.example.usermanagement.controller.interceptor;
//
//import com.example.usermanagement.domain.entity.Account;
//import com.example.usermanagement.domain.repository.AccountRepository;
//import com.example.usermanagement.service.AccountService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.web.servlet.HandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@RequiredArgsConstructor
//public class AccessTokenInterceptor implements HandlerInterceptor {
//
//    private final AccountService accountService;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
//        if (token == null) {
//            throw new IllegalArgumentException("Header 에 token 이 존재하지 않습니다.");
//        }
//        Account account = accountService.getAccountByToken(token);
//
//        request.setAttribute(HttpHeaders.AUTHORIZATION, account);
//
//        return true;
//    }
//}
