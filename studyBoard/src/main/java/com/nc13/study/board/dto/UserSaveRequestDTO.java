package com.nc13.study.board.dto;

import com.nc13.study.board.domain.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserSaveRequestDTO {
    private String username;
    private String password;
    private String nickname;

    @Builder
    public UserSaveRequestDTO(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .build();
    }
}
