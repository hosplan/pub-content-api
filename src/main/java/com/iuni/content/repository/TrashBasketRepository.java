package com.iuni.content.repository;

import com.iuni.content.domain.Task;
import com.iuni.content.domain.TrashBasket;
import com.iuni.content.helper.join.JoinTrash;
import com.iuni.content.helper.join.JoinTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface TrashBasketRepository extends JpaRepository<TrashBasket, Long> {
    @Query(value="SELECT tb.id AS id, tb.trash_id AS trashId, tb.type AS type, tb.create_date AS createDate, t.name AS name " +
            "FROM trash_basket AS tb " +
            "INNER JOIN task AS t ON tb.trash_id = t.id " +
            "AND tb.type = 0 " +
            "INNER JOIN task_member_map AS tm ON t.id = tm.task_id " +
            "WHERE tm.member_id = :memberId " +
            "AND (tm.map_type = 'owner' OR tm.map_type = 'editor') " +
            "UNION " +
            "SELECT tb.id AS id, tb.trash_id AS trashId, tb.type AS type, tb.create_date AS createDate, b.name AS name " +
            "FROM trash_basket AS tb " +
            "INNER JOIN board AS b ON tb.trash_id = b.id " +
            "AND tb.type = 1 " +
            "WHERE b.creator = :memberId " +
            "ORDER BY createDate DESC; ", nativeQuery = true)
    ArrayList<JoinTrash> findAllTrashByMemberId(@Param(value="memberId") Long memberId);

}
