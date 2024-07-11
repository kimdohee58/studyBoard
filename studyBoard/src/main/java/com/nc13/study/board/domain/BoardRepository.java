package com.nc13.study.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board save(Board board);

    Board findById(long id);

    Page<Board> findAll(Pageable pageable);

//    int findAllCount();
}
