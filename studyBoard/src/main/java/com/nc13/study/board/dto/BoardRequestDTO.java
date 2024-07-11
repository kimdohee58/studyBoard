package com.nc13.study.board.dto;

import com.nc13.study.board.domain.Board;
import com.nc13.study.board.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class BoardDTO {
    private String title;
    private String content;
    private User user;
    private Date entryDate;
    private Date modifyDate;

    public BoardDTO BoardResponseDTO(Board board) {

    }
    public BoardDTO BoardRequestDTO(Board board) {
        this.title = board.getTitle();
        this.content = board.getContent();
        this.user = board.getUser();
        this.entryDate = board.getEntryDate();
        this.modifyDate = board.getModifyDate();
    }

    @Builder
    public BoardDTO(int id, String title, String content, User user, Date entryDate, Date modifyDate) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.entryDate = entryDate;
        this.modifyDate = modifyDate;
    }

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .user(user)
                .entryDate(entryDate)
                .modifyDate(modifyDate)
                .build();
    }
}
