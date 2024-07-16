package com.nc13.study.board.controller;

import com.nc13.study.board.domain.User;
import com.nc13.study.board.dto.BoardRequestDTO;
import com.nc13.study.board.dto.BoardResponseDTO;
import com.nc13.study.board.service.BoardService;
import com.nc13.study.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final UserService userService;
    private final BoardService boardService;

    @GetMapping("/boards")
    public String boards(Model model, @PageableDefault(page = 1) Pageable pageable) {
        System.out.println("showBoard");

        Page<BoardResponseDTO> boardPages = boardService.paging(pageable);

        // blocklimit : page 개수 설정, 현재 사용자가 선택한 페이지 앞뒤로 5페이지씩만 보여준다.
        int blockLimit = 3;
        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), boardPages.getTotalPages());

//        model.addAttribute("users", users);
        model.addAttribute("boardPages", boardPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boards/boards";
    }

    @GetMapping("/boards/write")
    public String writeBoard() {
        return "boards/write";
    }

    @PostMapping("/boards/write")
    public String writeBoard(BoardRequestDTO boardRequestDTO, Model model) {
        System.out.println("writeBoard");
        boardService.save(boardRequestDTO);
        return "redirect:/boards";
    }
}
