package com.sparta.spartdelivery.domain.user.entity;

import com.sparta.spartdelivery.common.entity.Timestamped;
import com.sparta.spartdelivery.domain.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter // 모든 필드에 대한 getter 메서드를 자동 생성
@Entity // 이 클래스가 데이터베이스의 테이블로 사용됨을 나타냄
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성
@Table(name = "users") // 이 클래스와 연결된 테이블 이름
public class User extends Timestamped {

    @Id // 고유 식별자 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 설정
    private Long userId;

    @Column(unique = true) // 유일한 값이어야 함
    private String email;

    private String username; // 사용자의 이름 추가

    private String password; // 사용자의 비밀번호

    @Enumerated(EnumType.STRING) // 사용자 역할을 문자열로 저장
    private UserRole userRole;

    // 사용자 삭제 여부를 나타내는 필드
    private boolean deleted = false; // 기본값은 false (삭제되지 않음)

    // 사용자 생성 시 이메일, 사용자 이름, 비밀번호, 역할을 받는 생성자
    public User(String email, String username, String password, UserRole userRole) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }
    // 비밀번호를 변경하는 메서드
    public void changePassword(String password) {
        this.password = password;
    }
    // 사용자를 삭제 상태로 변경하는 메서드
    public void markAsDeleted() {
        this.deleted = true;
    }
}
