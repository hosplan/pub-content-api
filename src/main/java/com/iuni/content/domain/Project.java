package com.iuni.content.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Entity
@Table(name="project")
@Getter
@Setter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //프로젝트 이름
    @Column(name = "name")
    private String name;

    //프로젝트 설명
    @Column(name = "description", columnDefinition = "text")
    private String description;

    //프로젝트 시작 날짜
    @Column(name = "start_date")
    private Date startDate;

    //프로젝트 종료 날짜
    @Column(name = "end_date")
    private Date endDate;

    //프로젝트 이행날짜
    @Column(name = "due_date")
    private Date dueDate;

    //비공개 여부
    @Column(name = "is_private")
    private Boolean isPrivate;

    //삭제 여부
    @Column(name = "is_remove")
    private Boolean isRemove;

    @ManyToOne
    @JoinColumn(name="status_id", nullable=false)
    @ToString.Exclude
    private Status status;

    @ManyToOne
    @JoinColumn(name="stage_id")
    @ToString.Exclude
    private Stage stage;

}
