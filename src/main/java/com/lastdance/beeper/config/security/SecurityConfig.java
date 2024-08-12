package com.lastdance.beeper.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTTokenProvider jwtTTokenProvider;

    public SecurityConfig(JwtTTokenProvider jwtTTokenProvider) {
        this.jwtTTokenProvider = jwtTTokenProvider;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic(customizer -> customizer.disable()) // REST API는 UI를 사용하지 않으므로 기본설정을 비활성화
                .csrf(csrf -> csrf.disable()) // REST API는 csrf 보안이 필요 없으므로 비활성화
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // JWT Token 인증방식으로 세션은 필요 없으므로 비활성화
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/auth/**", "/exception/**", "/swagger-ui/**").permitAll()
                                .anyRequest().hasRole("ADMIN")) // 나머지 요청은 인증된 ADMIN만 접근 가능
                .exceptionHandling(exceptions ->
                        exceptions.accessDeniedHandler(new CustomAccessDeniedHandler())
                                .authenticationEntryPoint(new CustomAuthenticationEntryPoint())) // 예외 처리 핸들러 설정
                .addFilterBefore(new JwtAuthenticationFilter(jwtTTokenProvider),
                        UsernamePasswordAuthenticationFilter.class); // JWT Token 필터를 id/password 인증 필터 이전에 추가

        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-resources/**");
    }

    @Bean
    public HttpFirewall defaultHttpFirewall() {
        return new DefaultHttpFirewall();
    }
}