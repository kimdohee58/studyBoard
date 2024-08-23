package com.nc13.study.board.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// 순환참조 문제로 인해 따로 config 만들어서 사용해야 함, https://velog.io/@mooh2jj/Security-%EC%9D%B8%EC%A6%9D%EC%9D%B8%EA%B0%80%EC%B2%98%EB%A6%AC
@Configuration
public class PasswordEncorderConfig {
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
