//package com.iuni.content.repository;
//
//import com.iuni.content.domain.IuniStyle;
//import com.iuni.content.helper.join.JoinIuniStyle;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//
//import java.util.ArrayList;
//import java.util.Optional;
//
//public interface IuniStyleRepository extends JpaRepository<IuniStyle, Long> {
//    @Query(value="SELECT i.id, i.background, i.body, i.nose, i.left_white_eye as leftWhiteEye, i.left_eye as leftEye, i.right_white_eye as rightWhiteEye, i.right_eye as rightEye " +
//            "FROM iuni_style as i INNER JOIN member_iuni_map as m ON i.id = m.iuni_style_id " +
//            "WHERE m.member_id = :memberId AND i.is_main = true ", nativeQuery = true)
//    JoinIuniStyle getByMemberId(@Param(value="memberId") Long MemberId);
//}
