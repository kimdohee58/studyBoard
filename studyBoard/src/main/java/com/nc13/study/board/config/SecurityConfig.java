package com.nc13.study.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 보안 기능 비활성화
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // https://velog.io/@letsdev/Spring-Boot-3.1Spring-6.1-Security-Config-csrf-is-deprecated-and-marked-for-removal 참고
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests.anyRequest().permitAll()
                )
                // https://shoney.tistory.com/entry/Spring-%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-Security-Form-Login-%EC%9D%B8%EC%A6%9D-%EA%B8%B0%EB%B3%B8-%EC%84%A4%EC%A0%95-%EC%8A%A4%ED%94%84%EB%A7%81-3-%EB%B2%84%EC%A0%84-%EC%9D%B4%EC%83%81%EC%97%90%EC%84%9C-%EC%82%AC%EC%9A%A9-WebSecurityConfigurerAdapter-%EC%97%86%EC%9D%8C
                .formLogin(form -> form
                        .loginPage("/users/signIn")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/boards")
                        .failureUrl("/users/signIn")
                        .loginProcessingUrl("/users/signIn")
                        .permitAll()
                );
        return http.build();
    }
}
