package com.iuni.content.repository;

import com.iuni.content.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JwtRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
