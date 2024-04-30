package com.iuni.content.domain;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name="project_role")
@Getter
public class ProjectRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    //해당 프로젝트 생성 유저의 티어가 일정이상 되어야 됨
    @Column(name = "tier")
    private int tier;


}
