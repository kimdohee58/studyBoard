package com.nc13.study.board.dto;

import com.nc13.study.board.domain.Board;
import com.nc13.study.board.domain.User;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardResponseDTO {
    private int id;
    private String title;
    private String content;
    private String nickname;
    private Date entryDate;
    private Date modifyDate;

    public BoardResponseDTO(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.nickname = board.getUser().getNickname();
        this.entryDate = board.getEntryDate();
        this.modifyDate = board.getModifyDate();
    }
}
