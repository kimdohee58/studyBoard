package com.nc13.study.board.dto;

import com.nc13.study.board.domain.Board;
import com.nc13.study.board.domain.User;
import com.nc13.study.board.domain.UserRepository;
import lombok.*;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDTO {
    private String title;
    private String content;
    private User user;
    private Date entryDate;
    private Date modifyDate;

    public User setUser(User user) {
        return this.user = user;
    }

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .user(setUser(user))
                .entryDate(entryDate)
                .modifyDate(modifyDate)
                .build();
    }
}
