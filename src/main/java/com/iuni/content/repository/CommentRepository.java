package com.iuni.content.repository;

import com.iuni.content.domain.Comment;
import com.iuni.content.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.ArrayList;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<ArrayList<Comment>> findAllByTask(Task task);
}
