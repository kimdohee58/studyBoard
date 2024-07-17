package com.nc13.study.board.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nc13.study.board.domain.User;
import com.nc13.study.board.dto.UserResponseDTO;
import com.nc13.study.board.security.SecurityExceptionDTO;
import com.nc13.study.board.security.UserDetail;
import com.nc13.study.board.security.UserDetailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.io.IOException;
import java.io.PrintWriter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    private AuthenticationManager authenticationManager;
    private final UserDetailService userDetailService;

    public SecurityConfig(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    // 403, 401 error 처리, https://velog.io/@wonizizi99/Spring-Spring-Security-9-401-403-Error-ExceptionHandling-%ED%95%B4%EB%B3%B4%EA%B8%B0
//    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
//    private CustomAccessDeniedHandler customAccessDeniedHandler;

    // 보안 기능 비활성화
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // https://velog.io/@letsdev/Spring-Boot-3.1Spring-6.1-Security-Config-csrf-is-deprecated-and-marked-for-removal 참고
        http
                .csrf(AbstractHttpConfigurer::disable)
//                .sessionManagement((sessionManagement) ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .accessDeniedHandler(customAccessDeniedHandler) // 403 상태 코드 전달, 권한(인가) 예외처리
                        .authenticationEntryPoint(customAuthenticationEntryPoint) // 401 상태 코드 전달, 인증 예외처리
                )
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                                .requestMatchers(new AntPathRequestMatcher("/", "GET"),
                                        new AntPathRequestMatcher("/boards/**", "GET"),
                                        new AntPathRequestMatcher("/boards/**", "POST"),
                                        new AntPathRequestMatcher("/users/**", "GET"),
                                        new AntPathRequestMatcher("/users/**", "POST"),
//                                        new AntPathRequestMatcher("/header"),
                                        new AntPathRequestMatcher("/css/**"),
                                        new AntPathRequestMatcher("/images/**")
                                ).permitAll()
//                                .requestMatchers(
//                                        new AntPathRequestMatcher("/boards/**", "POST")
//                                ).hasAnyRole("ADMIN", "USER")
                                .anyRequest().permitAll()
//                        .anyRequest().permitAll()
                )
                // https://shoney.tistory.com/entry/Spring-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-Security-Form-Login-%EC%9D%B8%EC%A6%9D-%EA%B8%B0%EB%B3%B8-%EC%84%A4%EC%A0%95-%EC%8A%A4%ED%94%84%EB%A7%81-3-%EB%B2%84%EC%A0%84-%EC%9D%B4%EC%83%81%EC%97%90%EC%84%9C-%EC%82%AC%EC%9A%A9-WebSecurityConfigurerAdapter-%EC%97%86%EC%9D%8C
                .formLogin(form -> form
                                .loginPage("/users/signIn")
                                .usernameParameter("username")
                                .passwordParameter("password")
                                .defaultSuccessUrl("/boards")
//                                .failureUrl("/users/signIn")
                                .loginProcessingUrl("/users/auth")
//                                .successHandler(new AuthenticationSuccessHandler() {
//                                    @Override
//                                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                                        System.out.println("authentication success : " + authentication);
//                                        // https://velog.io/@rnqhstlr2297/Spring-Security%EC%99%80-JWT%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-Soical-Login-%EA%B5%AC%ED%98%84-refresh-%ED%86%A0%ED%81%B0-%EA%B5%AC%ED%98%84
//                                        response.sendRedirect("/boards");
////                                response.addCookie(); // https://velog.io/@duck-ach/JSP-%EC%BF%A0%ED%82%A4Cookie-%EC%A0%80%EC%9E%A5%ED%95%98%EA%B3%A0-%ED%99%9C%EC%9A%A9%ED%95%98%EA%B8%B0
//                                    }
//                                })
                                .failureHandler(new AuthenticationFailureHandler() {
                                    @Override
                                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                                        System.out.println("authentication failure : " + exception.getMessage());
                                        response.sendRedirect("/users/signIn");
                                    }
                                })
                                .permitAll()
                )
                // https://velog.io/@dailylifecoding/spring-security-logout-feature
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                        .logoutUrl("/users/logOut")
                        .addLogoutHandler(((request, response, authentication) -> {
                            SecurityContextHolder.clearContext();
                            HttpSession session = request.getSession();
                            if (session != null) {
                                session.invalidate();
                            }
                        }))
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.sendRedirect("/");
                        })
                );
//                .rememberMe(rememberMe -> rememberMe
//                        .key("key")
//                        .rememberMeParameter("rememberMe")
//                        .tokenValiditySeconds(3600)
//                        .userDetailsService(userDetailService)
//                        .authenticationSuccessHandler(loginSuccessHandler())
//                );
        return http.build();
    }

    // https://velog.io/@woosim34/401-Unauthorized-VS-403Forbidden-HTTP-%EC%83%81%ED%83%9C-%EB%B9%84%EA%B5%90
    private final AuthenticationEntryPoint customAuthenticationEntryPoint =
            (request, response, authException) -> {
                SecurityExceptionDTO fail = new SecurityExceptionDTO(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };

    private final AccessDeniedHandler customAccessDeniedHandler =
            (request, response, accessDeniedException) -> {
                SecurityExceptionDTO fail = new SecurityExceptionDTO(HttpStatus.FORBIDDEN.value(), "FORBIDDEN");
                response.setStatus(HttpStatus.FORBIDDEN.value());
                String json = new ObjectMapper().writeValueAsString(fail);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                PrintWriter writer = response.getWriter();
                writer.write(json);
                writer.flush();
            };
}
