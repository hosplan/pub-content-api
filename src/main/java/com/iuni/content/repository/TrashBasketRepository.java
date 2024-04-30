package com.iuni.content.repository;

import com.iuni.content.domain.Task;
import com.iuni.content.helper.join.JoinTrash;
import com.iuni.content.helper.join.JoinTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface TrashBasketRepository extends JpaRepository<Task, Long> {
    @Query(value="SELECT t.id, t.name, t.description, t.point, t.create_date as createDate ,t.update_date as updateDate, t.start_date as startDate, t.end_date as endDate, t.due_date as dueDate, t.status_id as statusId, t.minor_version as minorVersion, t.major_version as majorVersion, t.is_shared as isShared, btm.id as mapId, btm.id as mapId " +
            "FROM task_member_map as tm LEFT JOIN task as t ON tm.task_id = t.id LEFT JOIN board_task_map as btm ON t.id = btm.task_id " +
            "WHERE btm.board_id is null AND tm.member_id = :memberId ", nativeQuery = true)
    ArrayList<JoinTrash> findAllTrashByMemberId(@Param(value="memberId") Long memberId);

}
