package com.nc13.study.board.controller;

import com.nc13.study.board.domain.Role;
import com.nc13.study.board.domain.User;
import com.nc13.study.board.domain.UserRepository;
import com.nc13.study.board.dto.UserRequestDTO;
import com.nc13.study.board.dto.UserResponseDTO;
import com.nc13.study.board.service.UserService;
import jakarta.validation.Valid;
import jdk.swing.interop.SwingInterOpUtils;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.model.IModel;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    @Autowired
    private final UserService userService;
    @Autowired
    private UserRepository userRepository;

    // 회원가입
    @GetMapping("/users/signUp")
    public String users() {
        System.out.println("회원가입 접속");
        return "users/signUp";
    }

    @PostMapping("/users")
//    public String signUp(@Valid UserRequestDTO user) {
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

//    @PostMapping("/users/auth")
//    public String auth(UserRequestDTO user, Model model) {
////    public String auth(@AuthenticationPrincipal UserRequestDTO user, Model model) {
//        String username = user.getUsername();
//        String password = user.getPassword();
//
//        if (userService.findByUsername(username) == null) {
//            System.out.println(username + "회원 존재하지 않음");
//            return "redirect:/users/signIn";
//        }
//
//        UserResponseDTO logIn = userService.findByUsernameAndPassword(username, password);
//        if (logIn == null) {
//            System.out.println("사용자를 찾을 수 없음 : " + username);
//            return "redirect:/users/signIn";
//        }
//        System.out.println(logIn);
//        System.out.println("로그인 성공");
//
//        String logInName = username((Principal) logIn);
//        model.addAttribute("logIn", logInName);
//        System.out.println(logIn);
//        return "redirect:/boards";
//    }

    // https://velog.io/@yoho98/Spring-Security-%EB%A1%9C%EA%B7%B8%EC%9D%B8-%ED%9B%84-%EC%82%AC%EC%9A%A9%EC%9E%90-%EC%A0%95%EB%B3%B4%EC%96%BB%EA%B8%B0
    public String username(Principal principal) {
        System.out.println(principal.getName());
        return principal.getName();
    }
}
