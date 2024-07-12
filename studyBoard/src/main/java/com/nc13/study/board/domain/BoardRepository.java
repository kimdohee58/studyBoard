package com.nc13.study.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nc13.study.board.dto.BoardRequestDTO;
import com.nc13.study.board.dto.BoardResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board save(BoardRequestDTO board);

    Board findById(long id);

    Page<Board> findAll(Pageable pageable);
}
