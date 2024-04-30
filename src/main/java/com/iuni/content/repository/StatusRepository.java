package com.iuni.content.repository;

import com.iuni.content.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Optional<ArrayList<Status>> findByType(String type);
}
