package com.nc13.study.board.security;

import com.nc13.study.board.domain.User;
import com.nc13.study.board.domain.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    //------------------------------------------------------------
    // https://note-ydg.tistory.com/46
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username);

            // https://velog.io/@hellozin/Spring-Security-Form-Login-%EA%B0%84%EB%8B%A8-%EC%82%AC%EC%9A%A9-%EC%84%A4%EB%AA%85%EC%84%9C-f2jzojj8bj
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            if(user.getRole().equals("ADMIN")){
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            } else {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));
            }
            return new UserDetail(user);
        } catch (NoSuchElementException e) {
            throw new UsernameNotFoundException("Not Found " + username);
        }
    }

    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null && authentication.getPrincipal() != null){
            UserDetail userDetail = (UserDetail) authentication.getPrincipal();
            return userDetail.getUsername();
        }
        throw new UsernameNotFoundException("Not Found");
    }

//-------------------------------------------------------------------------
    // https://note-ydg.tistory.com/46
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        try {
//            User user = userRepository.findByUsername(username);
//            return new UserDetail(user);
//        } catch (NoSuchElementException e) {
//            throw new UsernameNotFoundException("Not Found " + username);
//        }
//    }
}
