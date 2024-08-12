package com.lastdance.beeper.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Enumeration;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTTokenProvider jwtTTokenProvider;

    public JwtAuthenticationFilter(JwtTTokenProvider jwtTTokenProvider) {
        this.jwtTTokenProvider = jwtTTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest servletRequest,
                                    HttpServletResponse servletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        String token = null;

        //헤더에서 Authorization에 해당하는 값 추출
        Enumeration<String> headers = servletRequest.getHeaders("Authorization");

        if(headers.hasMoreElements()){
            String authorizationHeader = headers.nextElement();
            token = authorizationHeader.replace("Bearer ", "");

            LOGGER.info("[doFilterInternal] token 값 추출 완료. token : {}", token);
        } else {
            LOGGER.info("[doFilterInternal] token 값 추출 불가. token : {}", token);
        }

        //유효성 검사
        LOGGER.info("[doFilterInternal] token 값 유효성 체크 시작");
        if (token != null && jwtTTokenProvider.validateToken(token)) {
            Authentication authentication = jwtTTokenProvider.getAuthentication(token); //사용자 존재 여부 확인
            SecurityContextHolder.getContext().setAuthentication(authentication);
            LOGGER.info("[doFilterInternal] token 값 유효성 체크 완료");
        }

        //HttpServletRequest, servletResponse를 chain으로 넘김.
        filterChain.doFilter(servletRequest, servletResponse);
    }
}