package com.nc13.study.board.config;

import com.nc13.study.board.error.CustomAccessDeniedHandler;
import com.nc13.study.board.error.CustomAuthenticationEntryPoint;
import com.nc13.study.board.security.UserDetailService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.ui.Model;

import java.io.IOException;

import static org.springframework.security.config.Customizer.withDefaults;

/*@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    private UserDetailService userDetailService;

    // https://minholee93.tistory.com/entry/Spring-Security-HTTP-Basic-Authentication-Spring-Boot-2
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
//                .userDetailsService(userDetailService); // https://fntg.tistory.com/189
                .inMemoryAuthentication().withUser("a").password("a").roles("ADMIN").and().withUser("b").password("b").roles("USER");
    }

    // 보안 기능 비활성화
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // https://velog.io/@letsdev/Spring-Boot-3.1Spring-6.1-Security-Config-csrf-is-deprecated-and-marked-for-removal 참고
        http
//                .httpBasic(CustomAccessDeniedHandler -)
                // JSESSIONID 사용안하기 위한 방법
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // basicAuth, https://velog.io/@woojjam/Spring-Security-HTTP-Basic-Authentication
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                        .requestMatchers("/", "/css/**", "/js/**", "/facicon.ico","/users/logOut").permitAll()
                        .anyRequest().authenticated()
                )
                // https://blog.naver.com/wizardkyn/220647117154
//                .formLogin(httpFormLoginConfigurer -> {
//                    httpFormLoginConfigurer.disable();
//                })
                // basicAuth end-----------------------------
                .csrf(AbstractHttpConfigurer::disable)
//                .exceptionHandling(authenticationManager -> authenticationManager
//                        .accessDeniedHandler(new CustomAccessDeniedHandler()) // 403 상태 코드 전달, 권한(인가) 예외처리
//                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 401 상태 코드 전달, 인증 예외처리
//                );
//                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
//                                .requestMatchers(new AntPathRequestMatcher("/", "GET"),
//                                        new AntPathRequestMatcher("/boards/**", "GET"),
//                                        new AntPathRequestMatcher("/users/**", "GET"),
//                                        new AntPathRequestMatcher("/users/**", "POST"),
//                                        new AntPathRequestMatcher("/css/**"),
//                                        new AntPathRequestMatcher("/images/**")
//                                ).permitAll()
//                                .requestMatchers(
//                                        new AntPathRequestMatcher("/boards/**", "POST")
//                                ).authenticated()
//                                .anyRequest().permitAll()
//                )
                // https://velog.io/@dailylifecoding/spring-security-logout-feature
//                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
//                        .logoutUrl("/users/logOut")
//                        .addLogoutHandler(((request, response, authentication) -> {
//                            SecurityContextHolder.clearContext();
//                            HttpSession session = request.getSession();
//                            if (session != null) {
//                                session.invalidate();
//                            }
//                        }))
//                        .logoutSuccessHandler((request, response, authentication) -> {
//                            response.sendRedirect("/");
//                        })
//                        .deleteCookies("JSESSIONID")
//                );
                .logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer
                                .logoutUrl("/users/logOut")
                                .addLogoutHandler(((request, response, authentication) -> {
//                            SecurityContextHolder.clearContext();
                                    try {
                                        response.sendRedirect("/");
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                    authentication.setAuthenticated(false);
                                }))
                                .logoutSuccessHandler((request, response, authentication) -> {
                                    authentication.setAuthenticated(true);
                                    response.sendRedirect("/");
                                })
                                .logoutSuccessUrl("/")
                                .deleteCookies("JSESSIONID") // https://velog.io/@dailylifecoding/spring-security-logout-feature
                )
                .httpBasic(withDefaults());

        return http.build();
    }

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        return (web)->web.ignoring().requestMatchers("/facicon.ico")
//    }
}*/

// 기존 formLogin 활용
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);
    private UserDetailService userDetailService;

    // 403, 401 error 처리, https://velog.io/@wonizizi99/Spring-Spring-Security-9-401-403-Error-ExceptionHandling-%ED%95%B4%EB%B3%B4%EA%B8%B0
//    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
//    private CustomAccessDeniedHandler customAccessDeniedHandler;

    // 보안 기능 비활성화
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // https://velog.io/@letsdev/Spring-Boot-3.1Spring-6.1-Security-Config-csrf-is-deprecated-and-marked-for-removal 참고
        http
                .csrf(AbstractHttpConfigurer::disable)
                // JSESSIONID 사용안하기 위한 방법
//                .sessionManagement((sessionManagement) ->
//                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                )
                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .accessDeniedHandler(new CustomAccessDeniedHandler()) // 403 상태 코드 전달, 권한(인가) 예외처리
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint()) // 401 상태 코드 전달, 인증 예외처리
                )
                .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                                .requestMatchers(new AntPathRequestMatcher("/", "GET"),
                                        new AntPathRequestMatcher("/boards/**", "GET"),
                                        new AntPathRequestMatcher("/users/**", "GET"),
                                        new AntPathRequestMatcher("/users/**", "POST"),
                                        new AntPathRequestMatcher("/css/**"),
                                        new AntPathRequestMatcher("/images/**")
                                ).permitAll()
                                .requestMatchers(
                                        new AntPathRequestMatcher("/boards/**", "POST"),
                                        new AntPathRequestMatcher("/replys","POST")
                                ).authenticated()
                                .anyRequest().permitAll()
                )
                // https://shoney.tistory.com/entry/Spring-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-Security-Form-Login-%EC%9D%B8%EC%A6%9D-%EA%B8%B0%EB%B3%B8-%EC%84%A4%EC%A0%95-%EC%8A%A4%ED%94%84%EB%A7%81-3-%EB%B2%84%EC%A0%84-%EC%9D%B4%EC%83%81%EC%97%90%EC%84%9C-%EC%82%AC%EC%9A%A9-WebSecurityConfigurerAdapter-%EC%97%86%EC%9D%8C
                .formLogin(form -> form
                                .loginPage("/users/signIn")
                                .usernameParameter("username")
                                .passwordParameter("password")
//                                .defaultSuccessUrl("/boards")
//                                .failureUrl("/users/signIn")
                                .loginProcessingUrl("/users/auth")
                                .successHandler(new AuthenticationSuccessHandler() {
                                    @Override
                                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                                        // https://velog.io/@rnqhstlr2297/Spring-Security%EC%99%80-JWT%EB%A5%BC-%EC%9D%B4%EC%9A%A9%ED%95%9C-Soical-Login-%EA%B5%AC%ED%98%84-refresh-%ED%86%A0%ED%81%B0-%EA%B5%AC%ED%98%84
                                        log.info("Authentication success");
                                        response.sendRedirect("/boards");
//                                response.addCookie(); // https://velog.io/@duck-ach/JSP-%EC%BF%A0%ED%82%A4Cookie-%EC%A0%80%EC%9E%A5%ED%95%98%EA%B3%A0-%ED%99%9C%EC%9A%A9%ED%95%98%EA%B8%B0
                                    }
                                })
                                .failureHandler(new AuthenticationFailureHandler() {
                                    @Override
                                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                                        log.info("authentication failure : " + exception);
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
                        .deleteCookies("JSESSIONID") // https://velog.io/@dailylifecoding/spring-security-logout-feature
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
//    private final AuthenticationEntryPoint customAuthenticationEntryPoint =
//            (request, response, authException) -> {
//                SecurityExceptionDTO fail = new SecurityExceptionDTO(HttpStatus.UNAUTHORIZED.value(), "UNAUTHORIZED");
//                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                String json = new ObjectMapper().writeValueAsString(fail);
//                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                PrintWriter writer = response.getWriter();
//                writer.write(json);
//                writer.flush();
//            };
//
//    private final AccessDeniedHandler customAccessDeniedHandler =
//            (request, response, accessDeniedException) -> {
//                SecurityExceptionDTO fail = new SecurityExceptionDTO(HttpStatus.FORBIDDEN.value(), "FORBIDDEN");
//                response.setStatus(HttpStatus.FORBIDDEN.value());
//                String json = new ObjectMapper().writeValueAsString(fail);
//                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//                PrintWriter writer = response.getWriter();
//                writer.write(json);
//                writer.flush();
//            };
}
