package com.iuni.content.repository;

import com.iuni.content.domain.Task;
import com.iuni.content.helper.join.JoinTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query(value="SELECT t.id, t.name, t.description, t.point, t.create_date as createDate ,t.update_date as updateDate, t.start_date as startDate, t.end_date as endDate, t.due_date as dueDate, t.status_id as statusId, t.minor_version as minorVersion, t.major_version as majorVersion, t.is_shared as isShared, s.name as statusName, m.task_order as taskOrder, m.id as mapId " +
            "FROM board_task_map as m INNER JOIN task as t ON m.task_id = t.id LEFT JOIN status as s ON s.id = t.status_id " +
            "WHERE m.board_id = :boardId AND t.is_delete != 't' " +
            "ORDER BY m.task_order", nativeQuery = true)
    ArrayList<JoinTask> findAllByBoardId(@Param(value="boardId") Long boardId);

    @Modifying
    @Transactional
    @Query(value="UPDATE task SET is_shared = :isShared WHERE id =:taskId", nativeQuery=true)
    int updateShare(@Param(value="taskId") Long taskId, @Param(value="isShared") boolean isShared);
}
