package com.nc13.study.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

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

    //    @Column(name = "writer_id")
    //    private int writerId;

    // User inner join
    @ManyToOne
    @JoinTable(name = "user", joinColumns = {@JoinColumn(name = "id", referencedColumnName = "writer_id")},
    inverseJoinColumns = {@JoinColumn(name = "writer_id")})
    private User user;

    public String getNickname() {
        return user.getNickname();
    }

    @Column(name = "entry_date")
    private Date entryDate;

    @Column(name = "modify_date")
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

}
