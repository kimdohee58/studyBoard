package com.nc13.study.board.controller;

import com.nc13.study.board.domain.Board;
import com.nc13.study.board.domain.User;
import com.nc13.study.board.dto.BoardRequestDTO;
import com.nc13.study.board.dto.BoardResponseDTO;
import com.nc13.study.board.security.UserDetail;
import com.nc13.study.board.security.UserDetailService;
import com.nc13.study.board.service.BoardService;
import com.nc13.study.board.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final UserService userService;
    private final UserDetailService userDetailService;
    private final BoardService boardService;

    @GetMapping("/boards")
    public String boards(@PageableDefault(page = 1) Pageable pageable, Model model,
                         @AuthenticationPrincipal UserDetail userDetail) {
        System.out.println("showBoard");
        System.out.println("userDetail: " + userDetail);
        if (userDetail != null) {
            User user = userDetail.getUser();
            System.out.println("user: " + user);
            System.out.println("로그인 성공");
            model.addAttribute("logIn", user);
        }

        Page<BoardResponseDTO> boardPages = boardService.paging(pageable);

        // blocklimit : page 개수 설정, 현재 사용자가 선택한 페이지 앞뒤로 5페이지씩만 보여준다.
        int blockLimit = 3;
        int startPage = (((int) Math.ceil(((double) pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        int endPage = Math.min((startPage + blockLimit - 1), boardPages.getTotalPages());

        model.addAttribute("boardPages", boardPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boards/boards";
    }

    @GetMapping("/boards/showOne/{id}")
    public String showOne(@RequestParam("id")Long id, Model model, @AuthenticationPrincipal UserDetail userDetail) {
        System.out.println("showOne");
        BoardResponseDTO boardResponseDTO = boardService.findById(id);
        model.addAttribute("board", boardResponseDTO);
        return "boards/showOne";
    }

    @GetMapping("/boards/write")
    public String writeBoard(Authentication authentication) {
        System.out.println(authentication);
        return "boards/write";
    }

    @PostMapping("/boards/write")
    public String writeBoard(BoardRequestDTO boardRequestDTO, Model model, Authentication authentication) {
        System.out.println("authentication.write: " + authentication);
        System.out.println("writeBoard");
        boardService.save(boardRequestDTO);
        return "redirect:/boards";
    }
}
