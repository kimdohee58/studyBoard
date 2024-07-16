package com.nc13.study.board.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
// https://velog.io/@wonizizi99/Spring-Spring-Security-9-401-403-Error-ExceptionHandling-%ED%95%B4%EB%B3%B4%EA%B8%B0
@Getter
@NoArgsConstructor
public class SecurityExceptionDTO {
    private int statusCode;
    private String message;

    public SecurityExceptionDTO(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}