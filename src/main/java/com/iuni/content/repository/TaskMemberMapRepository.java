package com.iuni.content.repository;

import com.iuni.content.domain.Task;
import com.iuni.content.domain.TaskMemberMap;
import com.iuni.content.helper.join.JoinTaskMemberMap;
import com.iuni.content.helper.join.JoinTaskPartyMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

public interface TaskMemberMapRepository extends JpaRepository<TaskMemberMap, Long> {
    @Query(value="SELECT m.name, m.nick_name as nickName, t.map_type as mapType, m.id as memberId " +
            "FROM task_member_map as t INNER JOIN member as m ON t.member_id = m.id " +
            "WHERE t.task_id = :taskId", nativeQuery = true)
    ArrayList<JoinTaskMemberMap> load(@Param(value="taskId") Long taskId);

    @Query(value="SELECT t.map_type as mapType, m.nick_name as nickName, m.email, m.id as memberId " +
            "FROM task_member_map as t INNER JOIN member as m ON t.member_id = m.id " +
            "WHERE t.task_id = :taskId", nativeQuery=true)
    ArrayList<JoinTaskPartyMember> findAllPartyMember(@Param(value="taskId") Long taskId);

    Optional<ArrayList<TaskMemberMap>> findAllByTaskAndMapType(Task task, String mapType);

    Optional<ArrayList<TaskMemberMap>> findByMemberIdAndMapType(Long memberId, String mapType);
    @Modifying
    @Transactional
    @Query(value="UPDATE task_member_map SET map_type = :mapType WHERE map_type ='editor' AND task_id = :taskId", nativeQuery = true)
    int updatePreviousEditor(@Param(value="mapType") String mapType,@Param(value="taskId") Long taskId);

    @Modifying
    @Transactional
    @Query(value="UPDATE task_member_map SET map_type =:mapType WHERE task_id =:taskId AND member_id =:memberId AND map_type !='owner'", nativeQuery = true)
    int updateNewEditor(@Param(value="mapType") String mapType, @Param(value="taskId") Long taskId, @Param(value="memberId") Long memberId);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM task_member_map WHERE task_id = :taskId AND member_id = :memberId", nativeQuery = true)
    int deleteMap(@Param(value="taskId") Long taskId, @Param(value="memberId") Long memberId);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM task_member_map WHERE task_id = :taskId", nativeQuery = true)
    int deleteMapByTaskId(@Param(value="taskId") Long taskId);

    @Modifying
    @Transactional
    @Query(value="DELETE FROM task_member_map WHERE member_id = :memberId", nativeQuery = true)
    int deleteMapByMemberId(@Param(value="memberId") Long memberId);

    @Modifying
    @Transactional
    @Query(value="UPDATE task_member_map SET map_type = :mapType WHERE member_id = :memberId AND task_id = :taskId", nativeQuery = true)
    int updateShareRequest(@Param(value="memberId") Long memberId, @Param(value="taskId") Long taskId, @Param(value="mapType") String type);

    Optional<ArrayList<TaskMemberMap>> findAllById(Long taskId);

}
