package com.nc13.study.board.service;

import com.nc13.study.board.domain.Board;
import com.nc13.study.board.domain.BoardRepository;
import com.nc13.study.board.dto.BoardRequestDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepo;

    @Transactional
    public void save(BoardRequestDTO board) {
        boardRepo.save(board.toEntity());
    }

    @Transactional
    public Board findById(Long id) {
        return boardRepo.findById(id).orElse(null);
    }

    @Transactional
    public List<Board> findAll() {
        return boardRepo.findAll();
    }

    // 페이징
    @Transactional
    public Page<BoardRequestDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1; //page 위치에 있는 값은 0부터 시작
        int pageLimit = 5; // 한페이지에 보여줄 글 개수

        // 한 페이지에 5개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순
        Page<Board> boardsPages = boardRepo.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        // 목록 : title, writer, entryDate
        Page<BoardRequestDTO> boardRequestDTOS = boardsPages.map(
                boardPage -> new BoardRequestDTO(boardPage)
        );
        return boardRequestDTOS;
    }
}
