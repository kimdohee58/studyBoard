package com.nc13.study.board.controller;

import com.nc13.study.board.domain.Board;
import com.nc13.study.board.domain.ReplyRepository;
import com.nc13.study.board.domain.User;
import com.nc13.study.board.dto.BoardRequestDTO;
import com.nc13.study.board.dto.BoardResponseDTO;
import com.nc13.study.board.dto.ReplyDTO;
import com.nc13.study.board.security.UserDetail;
import com.nc13.study.board.security.UserDetailService;
import com.nc13.study.board.service.BoardService;
import com.nc13.study.board.service.ReplyService;
import com.nc13.study.board.service.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.flogger.Flogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {
    private final UserService userService;
    private final UserDetailService userDetailService;
    private final BoardService boardService;
    private final ReplyRepository replyRepository;
    private final ReplyService replyService;

    private static final Logger logger = Logger.getLogger(BoardController.class.getName());

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

    @GetMapping("/boards/{id}")
    public String showOne(@PathVariable("id") Long id, Model model, @AuthenticationPrincipal UserDetail userDetail) {
        System.out.println("showOne");
        System.out.println("userDetail: " + userDetail);
        Optional<Board> board = boardService.findById(id);
        BoardResponseDTO boardResponseDTO = new BoardResponseDTO(board.orElse(null));
        if (userDetail != null) {
            User user = userDetail.getUser();
            model.addAttribute("logIn", user);
        }
        model.addAttribute("board", boardResponseDTO);

        // reply
        System.out.println("showReply");
        List<ReplyDTO> replies = replyService.findAllByBoardId((long) boardResponseDTO.getId());
        if (replies.isEmpty()) {
            model.addAttribute("replies", null);
        } else {
            model.addAttribute("replies", replies);
        }
        return "boards/showOne";
    }

    @GetMapping("/boards/new")
    public String newBoard(@AuthenticationPrincipal UserDetail userDetail) {
        System.out.println("write board---------------------------------------------------");
        if(userDetail == null) {
            return "redirect:/boards";
        }
        log.error("board new");
        return "boards/new";
    }

    @PostMapping("/boards/new")
    public String newBoard(BoardRequestDTO boardRequestDTO, @AuthenticationPrincipal UserDetail userDetail, Model model, Authentication authentication) {
        System.out.println("writeBoard");
        System.out.println(boardRequestDTO.getTitle());
        System.out.println(boardRequestDTO.getContent());
        System.out.println("boardRequestDTO: " + boardRequestDTO);
        System.out.println("userDetail: " + userDetail);
        if (userDetail == null) {
            System.out.println("권한이 없습니다.");
            return "redirect:/boards";
        }
        boardRequestDTO.setUser(userDetail.getUser());
        boardService.save(boardRequestDTO);
        System.out.println("작성 완료");
        return "redirect:/boards";
    }
}
