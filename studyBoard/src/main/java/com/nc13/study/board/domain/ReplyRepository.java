package com.nc13.study.board.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    Reply save(Reply reply);

//    Reply findByReplyId(Long id);

    List<Reply> findAllByBoardId(Long boardId);
}
