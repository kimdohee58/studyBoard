package com.nc13.study.board.service;

import com.nc13.study.board.dto.UserSaveRequestDTO;
import com.nc13.study.board.domain.User;
import com.nc13.study.board.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void save(UserSaveRequestDTO user) {
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
        userRepo.findByUsernameAndPassword(username, password);
    }
}
