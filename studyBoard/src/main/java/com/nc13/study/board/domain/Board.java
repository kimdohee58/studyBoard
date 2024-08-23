package com.nc13.study.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
@ToString(exclude = "user")
@Entity
@Getter
@Table(name = "board")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    // User left join, https://ksh-coding.tistory.com/105
    @ManyToOne(fetch = FetchType.LAZY) // false로 설정하게 되면 user가 null 값인 것을 허용하지 않음
    @JoinColumn(name = "writer_id")
    private User user;

    @Column(name = "entry_date")
    @CreationTimestamp
    private Date entryDate;

    @Column(name = "modify_date")
    @CreationTimestamp
    private Date modifyDate;

    @Builder
    public Board(int id, String title, String content, User user, Date entryDate, Date modifyDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
        this.entryDate = entryDate;
        this.modifyDate = modifyDate;
    }

    @Builder
    public Board(Board board) {
        this.id = board.id;
        this.title = board.title;
        this.content = board.content;
        this.user = board.user;
        this.entryDate = board.entryDate;
        this.modifyDate = board.modifyDate;
    }
}
