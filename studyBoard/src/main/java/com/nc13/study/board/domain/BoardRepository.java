package com.nc13.study.board.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nc13.study.board.dto.BoardRequestDTO;
import com.nc13.study.board.dto.BoardResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board save(Board board);

    Board findById(long id);

    Page<Board> findAll(Pageable pageable); // https://velog.io/@xogml951/JPA-N1-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0-%EC%B4%9D%EC%A0%95%EB%A6%AC
}
