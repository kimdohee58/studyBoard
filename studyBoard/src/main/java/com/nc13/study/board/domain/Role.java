package com.nc13.study.board.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role { // https://dev-coco.tistory.com/120
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String role;
}