package com.nc13.study.board.service;

import com.nc13.study.board.domain.Board;
import com.nc13.study.board.domain.BoardRepository;
import com.nc13.study.board.dto.BoardRequestDTO;
import com.nc13.study.board.dto.BoardResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepo;

    // 페이징
    @Transactional
    public Page<BoardResponseDTO> paging(Pageable pageable) {
        int page = pageable.getPageNumber() - 1; //page 위치에 있는 값은 0부터 시작
        int pageLimit = 5; // 한페이지에 보여줄 글 개수

        // 한 페이지에 5개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순
        Page<Board> boardsPages = boardRepo.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));

        // 목록 : title, writer, entryDate
        Page<BoardResponseDTO> boardResponseDTOS = boardsPages.map(
                boardPage -> new BoardResponseDTO(boardPage)
        );
        return boardResponseDTOS;
    }

    @Transactional
    public void save(BoardRequestDTO board) {
        boardRepo.save(board.toEntity());
    }

    @Transactional
    public Optional<Board> findById(Long id) {
        Optional<Board> board = boardRepo.findById(id);
        return Optional.ofNullable(board.orElse(null));
    }

    @Transactional
    public List<Board> findAll() {
        return boardRepo.findAll();
    }
}
