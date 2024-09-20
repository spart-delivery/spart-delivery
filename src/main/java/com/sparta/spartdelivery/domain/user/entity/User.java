package com.sparta.spartdelivery.domain.user.entity;

import com.sparta.spartdelivery.common.dto.AuthUser;
import com.sparta.spartdelivery.common.entity.Timestamped;
import com.sparta.spartdelivery.domain.user.enums.UserRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "users")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    // 사용자 삭제 여부를 확인하는 메서드
    private boolean deleted = false;

    public User(String email, String password, UserRole userRole) {
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }

    private User(Long id, String email, UserRole userRole) {
        this.userId = id;
        this.email = email;
        this.userRole = userRole;
    }

    public static User fromAuthUser(AuthUser authUser) {
        return new User(authUser.getId(), authUser.getEmail(), authUser.getUserRole());
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void updateRole(UserRole userRole) {
        this.userRole = userRole;
    }

    // 사용자 상태를 '탈퇴'로 변경하는 메서드
    public void markAsDeleted() {
        this.deleted = true;
    }
}
