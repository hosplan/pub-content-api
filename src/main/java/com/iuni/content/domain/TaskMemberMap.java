package com.iuni.content.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name="task_member_map")
@Getter
@Setter
public class TaskMemberMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="task_id")
    @JsonIgnore
    private Task task;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name= "map_type")
    private String mapType;

    @Transient
    private String nickName;

    @Transient
    private String name;

    @Transient
    private Long taskId;
}
