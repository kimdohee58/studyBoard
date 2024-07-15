package com.nc13.study.board.service;

import com.nc13.study.board.dto.UserRequestDTO;
import com.nc13.study.board.domain.User;
import com.nc13.study.board.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void save(UserRequestDTO user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepo.save(user.toEntity());
    }

    @Transactional
    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Transactional
    public void findByUsernameAndPassword(String username, String password) {
        User user = userRepo.findByUsernameAndPassword(username, passwordEncoder.encode(password));
        System.out.println("UserService : "+user);
    }

    @Transactional
    public List<User> findAll() {
        return userRepo.findAll();
    }
}
