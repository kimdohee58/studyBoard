//package com.nc13.study.board.dto;
//
//import com.nc13.study.board.domain.Reply;
//import lombok.*;
//
//import java.util.Date;
//
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class ReplyResponseDTO {
//    private int id;
//    private String content;
//    private int writerId;
//    private int boardId;
//    private int step;
//    private int depth;
//    private Date entryDate;
//    private Date modifyDate;
//
//    public ReplyResponseDTO(Reply reply) {
//        this.id = reply.getId();
//        this.content = reply.getContent();
//        this.writerId = reply.getUser().getId();
//        this.boardId = reply.getBoard().getId();
//        this.step = reply.getStep();
//        this.depth = reply.getDepth();
//        this.entryDate = reply.getEntryDate();
//        this.modifyDate = reply.getModifyDate();
//    }
//}
