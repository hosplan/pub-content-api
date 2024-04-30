package com.iuni.content.repository;

import com.iuni.content.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Optional<List<Board>> findAllByCreatorAndIsDeleteOrderByBoardOrderDesc(Long userId, Boolean isDelete);
}
