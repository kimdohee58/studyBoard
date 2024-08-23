package com.nc13.study.board.controller;

import com.nc13.study.board.domain.Board;
import com.nc13.study.board.domain.User;
import com.nc13.study.board.dto.BoardResponseDTO;
import com.nc13.study.board.dto.ReplyDTO;
import com.nc13.study.board.security.UserDetail;
import com.nc13.study.board.service.BoardService;
import com.nc13.study.board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ReplyController {
    private static final Logger log = LoggerFactory.getLogger(ReplyController.class);
    private final ReplyService replyService;
    private final BoardService boardService;

    // 댓글 등록
    @GetMapping("/replys")
    public String writeReply(@RequestParam("boardId") int boardId, Model model, @AuthenticationPrincipal UserDetail user) {
        if (user == null) {
            return "redirect:/users/signIn";
        }
        System.out.println("boardId: " + boardId);
        model.addAttribute("boardId", boardId);
        return "replys/write";
    }

    @PostMapping("/replys")
    public String writeReply(@RequestParam("boardId") int boardId, ReplyDTO replyDTO, @AuthenticationPrincipal UserDetail userDetail) {
        System.out.println("------------------------------");
        System.out.println("write reply");
        System.out.println("replyDTO: " + replyDTO);
        System.out.println(replyDTO.getContent());
        System.out.println(replyDTO.getBoardId());
        System.out.println("------------------------------");

        if (userDetail == null) {
            return "redirect:/users/signIn";
        }
        System.out.println("userDetail: "+userDetail.getUser());
        replyDTO.setUser(userDetail.getUser());
        System.out.println("replyDTO userId: "+replyDTO.getUser().getId());

        Optional<Board> board = boardService.findById(Long.valueOf(boardId));
//        replyDTO.setBoard(board);

        System.out.println("replyDTO: " + replyDTO);

        replyService.save(replyDTO);
        log.debug("reply saved");
        return "redirect:/boards/" + replyDTO.getBoardId();
    }

    // 대댓글 등록
//    @GetMapping("/replys/{id}")
//    public String writeReplys(@PathVariable Long id, ReplyDTO replyDTO, Model model, @AuthenticationPrincipal UserDetail user) {
//        if (user == null) {
//            return "redirect:/login";
//        }
//        System.out.println("replyDTO: " + replyDTO);
//
//        replyDTO.setUser(user.getUser().getId());
//        replyDTO.setBoard(boardId);
//
//        replyService.save(replyDTO);
//        log.debug("reply saved");
//        return "redirect:/boards/showOne/" + replyDTO.getBoardId();
//    }
}
