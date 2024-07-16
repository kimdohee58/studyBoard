package com.nc13.study.board.controller;

import com.nc13.study.board.domain.User;
import com.nc13.study.board.dto.UserRequestDTO;
import com.nc13.study.board.service.UserService;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.model.IModel;

@Controller
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;

    // 회원가입
    @GetMapping("/users/signUp")
    public String users() {
        System.out.println("회원가입 접속");
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
        System.out.println("로그인 접속");
        return "users/signIn";
    }

    @PostMapping("/users/auth")
    public String auth(UserRequestDTO user, Model model) {
        String username = user.getUsername();
        String password = user.getPassword();

        if (userService.findByUsername(username) == null) {
            System.out.println(username + "회원 존재하지 않음");
            return "redirect:/users/signIn";
        }

        User user1 = userService.findByUsernameAndPassword(username, password);
        if (user1 == null) {
            System.out.println("사용자를 찾을 수 없음 : " + user1);
            return "redirect:/users/signIn";
        }

        model.addAttribute("user", user1.getNickname());
        System.out.println("로그인 성공");
        return "redirect:/boards";
    }
}
