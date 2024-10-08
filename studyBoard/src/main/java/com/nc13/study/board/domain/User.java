package com.nc13.study.board.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;

import java.awt.dnd.DropTargetEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/* @Entity가 사용된 영역은 도메인 모델, 비지니스 처리를 담당
이는 도메인이라 불리는 개발 대상을 모든 사람이 동일한 관점에서 이해할 수 있고, 공유할 수 있도록 단순화 시킨 것 */

@Entity
@Getter // entity 클래스에서는 절대 Setter 메소드를 쓰지 않음
@Table(name = "user") // db상의 실제 테이블명을 명시하여 엔티티와 db table 매핑
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// lombok에서 제공, 매개변수를 갖지 않는 기본 생성자를 생성해주며 일반적으로 access=AccessLevel.PROTECTED 옵션 선언해서 무분별한 객체 생성 방지
public class User {
    @Id // 해당 변수가 primary key(PK) 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // 스프링 부트 2.0에서는 GenerationType.IDENTITY 옵션을 추가해야만 auto_increment가 된다.
    // @GeneratedValue를 통해 자동 생성, 생성 전략=IDENTITY로 고유키 생성전략 사용
    // 이는 mySQL의 AUTO_INCRESEMENT와 같은 역할
    @Column(name = "id", nullable = false)
    private int id;

    // @Column 사용해서 테이블의 컬럼과 연결
    @Column(name = "email", nullable = false)
//    @Email // email 형식으로 받기
//    @Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z]+[.]+[a-zA-z.]+") // 얘 사용해도 됨 아마도
    @NotBlank(message = "아이디는 필수 값입니다.")
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank(message = "비밀번호는 필수 값입니다.")
    private String password;

    @Column(name = "nickname", nullable = false)
    @NotBlank(message = "닉네임은 필수 값입니다.")
    private String nickname;

    @Enumerated(EnumType.STRING) // enumtype.string 옵션 사용하면 enum 이름 그대로 db에 저장
    @Column(name = "role", nullable = false)
    @ColumnDefault("'ROLE_USER")
    private Role role;

    @Column(name = "create_at")
    @CreationTimestamp
    private Date entryDate;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private Date updateDate;

    // https://junghwanta.tistory.com/5
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Board> boardList;

    @Builder
    public User(User user) {
        this.id = user.id;
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.role = user.getRole();
        this.entryDate = user.getEntryDate();
        this.updateDate = user.getUpdateDate();
    }

    @Builder
    // 롬복에서 제공해주는 어노테이션으로, 클래스에 빌더 패턴을 자동으로 생성해 줍니다. 빌더 패턴은 생성자 또는 자바 빈 패턴 보다 객체 생성을 가독성 있고 편리하게 해주는 디자인 패턴 중 하나로, 테스트 과정에서 자세히 알아보겠습니다.
    public User(int id, String username, String password, String nickname, Role role, Date entryDate, Date updateDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.role = role.USER; // 여기서 기본값으로 USER 세팅
        this.entryDate = entryDate;
        this.updateDate = updateDate;
    }

    public User(String username, String password, List<GrantedAuthority> authorityList) {
        this.username = username;
        this.password = password;
        this.role = Role.USER;
//        this.role = authorityList.isEmpty() ? Role.USER : NULL;
    }
}