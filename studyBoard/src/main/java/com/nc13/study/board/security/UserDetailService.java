package com.nc13.study.board.security;

import com.nc13.study.board.domain.User;
import com.nc13.study.board.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    // https://note-ydg.tistory.com/46
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username);
            return new UserDetail(user);
        } catch (NoSuchElementException e) {
            throw new UsernameNotFoundException("Not Found " + username);
        }
    }
}
