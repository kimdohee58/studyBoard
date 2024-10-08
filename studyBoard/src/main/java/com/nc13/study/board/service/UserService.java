package com.nc13.study.board.service;

import com.nc13.study.board.dto.UserRequestDTO;
import com.nc13.study.board.domain.User;
import com.nc13.study.board.domain.UserRepository;
import com.nc13.study.board.dto.UserResponseDTO;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
//@Validated // 유효성 검사하기 위함
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;


    // https://dev-coco.tistory.com/123
    @Transactional
    public Map<String, String> validateHandling(Errors errors) {
        Map<String, String> errorMap = new HashMap<>();

        for (FieldError fieldError : errors.getFieldErrors()) {
            String validKeyName = String.format("valid_%s", fieldError.getField());
            errorMap.put(validKeyName, fieldError.getDefaultMessage());
        }
        return errorMap;
    }

    @Transactional
//    public void save(@Valid UserRequestDTO user) {
    public void save(UserRequestDTO user) {
        /*// 추가한 부분
        Optional<User> userOpt = Optional.ofNullable(userRepo.findByUsername(user.getUsername()));
        if (userOpt.isPresent()) {
            throw new BadCredentialsException("Username is already taken");
        }
        //-----*/

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user.toEntity());
    }

    // 주석처리하면 안됨!
    @Transactional
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    // https://kedric-me.tistory.com/entry/Spring-Password-Encoder-%EB%B9%84%EB%B0%80%EB%B2%88%ED%98%B8-%EC%95%94%ED%98%B8%ED%99%94#google_vignette,
    @Transactional
    public UserResponseDTO findByUsernameAndPassword(String username, String password) {
        User user = userRepo.findByUsername(username);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return null;
        }
        return new UserResponseDTO(user);
    }

    @Transactional
    public List<User> findAll() {
        return userRepo.findAll();
    }
}
