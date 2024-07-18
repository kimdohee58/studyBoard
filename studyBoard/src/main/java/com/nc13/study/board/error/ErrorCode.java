package com.nc13.study.board.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;
// https://non-stop.tistory.com/667
@Getter
public enum ErrorCode {
    UNAUTHORIZED_CLIENT(HttpStatus.BAD_REQUEST, "서버 오류"),
    FORBIDDEN_CLIENT(HttpStatus.FORBIDDEN, "접근 권한이 없음."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 토큰");

    private HttpStatus httpStatus;
    private String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
