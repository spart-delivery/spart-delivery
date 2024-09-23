package com.sparta.spartdelivery.domain.user.repository;

import com.sparta.spartdelivery.domain.user.entity.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    // 사용자 이메일 중복 체크
    boolean existsByEmail(String email);

    // 사용자 이름 중복 체크
    boolean existsByUsername(String username);
}
