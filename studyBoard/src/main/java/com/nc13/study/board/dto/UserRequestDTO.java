package com.nc13.study.board.dto;

import com.nc13.study.board.domain.Role;
import com.nc13.study.board.domain.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.EmbeddableInstantiator;

import java.util.Date;

import static org.hibernate.cfg.JdbcSettings.USER;

@Data
@NoArgsConstructor
public class UserRequestDTO {
    @NotBlank(message = "회원 ID는 필수 입력 항목입니다.")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$", message = "메일 형식이 아닙니다.")
    private String username;
    @NotBlank(message = "회원 PW는 필수 입력 항목입니다.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String password;
    @NotBlank(message = "회원 닉네임은 필수 입력 항목입니다.")
    private String nickname;
    private Role role;
    private Date entryDate;
    private Date updateDate;

    public User toEntity() {
        return User.builder()
                .username(username)
                .password(password)
                .nickname(nickname)
                .role(role)
                .entryDate(entryDate)
                .updateDate(updateDate)
                .build();
    }
}
