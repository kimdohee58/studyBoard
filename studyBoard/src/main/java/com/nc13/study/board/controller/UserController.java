package com.nc13.study.board.controller;

import com.nc13.study.board.dto.UserRequestDTO;
import com.nc13.study.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    // 회원가입
    @GetMapping("/users/signUp")
    public String users() {
        return "users/signUp";
    }

    @PostMapping("/users")
    public String signUp(UserRequestDTO user) {
        userService.save(user);
        System.out.println("회원가입 성공");
        return "redirect:/users/signIn";
    }

    // 로그인
    @GetMapping("/users/signIn")
    public String signIn() {
        return "users/signIn";
    }

    @PostMapping("/users/auth")
    public String auth(@Param("username") String username, @Param("password") String password) {
        if (userService.findByUsername(username) == null) {
            return "redirect:/users/signIn";
        }
        userService.findByUsernameAndPassword(username, password);
        System.out.println("로그인 성공");
        return "redirect:/boards";
    }
}
