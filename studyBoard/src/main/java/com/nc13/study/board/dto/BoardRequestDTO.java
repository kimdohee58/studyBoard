package com.nc13.study.board.dto;

import com.nc13.study.board.domain.Board;
import com.nc13.study.board.domain.User;
import lombok.*;

import java.util.Date;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardRequestDTO {
    private String title;
    private String content;
    private int writerId;
    private Date entryDate;
    private Date modifyDate;

    public User setUser(int writerId) {
        return User.builder().build();
    }

    @Builder
    public BoardRequestDTO(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writerId = board.getUser().getId();
        this.entryDate = board.getEntryDate();
        this.modifyDate = board.getModifyDate();
    }

    public Board toEntity(Board board) {
        return Board.builder()
                .title(title)
                .content(content)
                .user(setUser(writerId))
                .entryDate(entryDate)
                .modifyDate(modifyDate)
                .build();
    }
}
