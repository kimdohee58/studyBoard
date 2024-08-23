package com.nc13.study.board.dto;

import com.nc13.study.board.domain.Board;
import com.nc13.study.board.domain.Reply;
import com.nc13.study.board.domain.User;
import lombok.*;

import java.util.Date;
import java.util.Optional;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
    private int id;
//    @NotBlank(message = "내용은 필수 입력 항목입니다.")
    private String content;
    private int writerId;
    private String nickname;
    private int boardId;
    private int step;
    private int depth;
    private Date entryDate;
    private Date modifyDate;
    private User user;
    private Board board;

    // reponse
    public ReplyDTO(Reply reply) {
        this.id = reply.getId();
        this.content = reply.getContent();
        this.nickname = reply.getUser().getNickname(); // DTO 전달할 때 nickname 출력을 위해서
//        this.boardId = reply.getBoard().getId();
        this.boardId = reply.getBoardId();
        this.step = reply.getStep();
        this.depth = reply.getDepth();
        this.entryDate = reply.getEntryDate();
        this.modifyDate = reply.getModifyDate();
    }

    // request
    public User setUser(User user) {
        return this.user = user;
    }

    public Board setBoard(Board board) {
        return this.board = board;
    }

    public Reply toEntity() {
        return Reply.builder()
                .content(content)
                .user(setUser(user))
                .boardId(boardId)
                .step(step)
                .depth(depth)
                .entryDate(entryDate)
                .modifyDate(modifyDate)
                .build();
    }
}
