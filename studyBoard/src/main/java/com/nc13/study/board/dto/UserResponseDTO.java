package com.nc13.study.board.dto;

import com.nc13.study.board.domain.Role;
import com.nc13.study.board.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private int id;
    private String username;
    private String password;
    private String nickname;
    private Role role;

    @Builder
    public UserResponseDTO(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.role = user.getRole();
    }
}
