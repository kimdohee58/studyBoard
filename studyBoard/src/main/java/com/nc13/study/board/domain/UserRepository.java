package com.nc13.study.board.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/* DB와 같이 데이터 저장소에 접근하는 영역(DAO, Data Access Object) */

// Entity랑 같은 위치에 있다면 @Repository 안해도됨
//@Repository // 이렇게 class 위에 역할에 따라 어노테이션 선언해주면 자동으로 bean 등록 완료
public interface UserRepository extends JpaRepository<User, Integer> { // 제네릭 타입 매개변수 T와 ID 사용, T : 레파지토리가 조작할 JPA 엔티티 타입, ID : 엔티티의 PK(일반적으로 long, int) 타입 의미
    // username 존재하는지 확인
    User findByUsername(String username); // 규칙에 맞게 Method 이름 만들면 Query문 정의하지 않아도 ㄱㅊ

    // 회원가입 메소드
    User save(User user);

    // 로그인 메소드
    User findByUsernameAndPassword(String username, String password);

    // 회원 목록
    List<User> findAll();
}
