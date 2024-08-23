package com.nc13.study.board.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.Date;

@Entity
@Getter
@Table(name = "reply")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private User user;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "board_id")
//    private Board board;
    @Column(name = "board_id")
    private int boardId;

    @Column(name = "step")
    private int step;

    @Column(name = "depth")
    private int depth;

    @Column(name = "entry_date")
    private Date entryDate;

    @Column(name = "modify_date")
    private Date modifyDate;

//    @Builder
//    public Reply(int id, String content, User user, Board board, int step, int depth, Date entryDate, Date modifyDate) {
//        this.id = id;
//        this.content = content;
//        this.user = user;
//        this.board = board;
//        this.step = step;
//        this.depth = depth;
//        this.entryDate = entryDate;
//        this.modifyDate = modifyDate;
//    }
@Builder
public Reply(int id, String content, User user, int boardId, int step, int depth, Date entryDate, Date modifyDate) {
    this.id = id;
    this.content = content;
    this.user = user;
    this.boardId = boardId;
    this.step = step;
    this.depth = depth;
    this.entryDate = entryDate;
    this.modifyDate = modifyDate;
}
}
