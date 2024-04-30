package com.iuni.content.repository;

import com.iuni.content.domain.Board;
import com.iuni.content.domain.BoardTaskMap;
import com.iuni.content.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BoardTaskMapRepository extends JpaRepository<BoardTaskMap, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM board_task_map WHERE task_id = :taskId", nativeQuery = true)
    int deleteAllByTaskId(@Param(value="taskId") Long taskId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM board_task_map WHERE board_id =:boardId", nativeQuery = true)
    int deleteAllByBoardId(@Param(value="boardId") Long boardId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE board_task_map SET task_order =:taskOrder WHERE board_id =:boardId AND task_id =:taskId", nativeQuery = true)
    int updateTaskOrder(@Param(value="boardId") Long boardId, @Param(value="taskId") Long taskId, @Param(value="taskOrder") int taskOrder);

    Optional<BoardTaskMap> findByBoardAndTask(Board board, Task task);

    @Modifying
    @Transactional
    @Query(value= "UPDATE board_task_map SET board_id =:updateBoardId WHERE board_id =:boardId AND task_id =:taskId", nativeQuery = true)
    int updateMoveTask(@Param(value="boardId") Long boardId, @Param(value="updateBoardId") Long updateBoardId, @Param(value="taskId") Long taskId);
}
