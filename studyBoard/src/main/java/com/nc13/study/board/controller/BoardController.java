package com.nc13.study.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
    @GetMapping("/boards")
    public String boards(Model model) {
        return "boards/boards";
    }
}
