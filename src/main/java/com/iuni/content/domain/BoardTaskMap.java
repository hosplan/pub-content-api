package com.iuni.content.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="boardTaskMap")
@Getter
@Setter
public class BoardTaskMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    @ToString.Exclude
    private Board board;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    @ToString.Exclude
    private Task task;

    @Transient
    private Long taskId;

    @Transient
    private Long boardId;

    @Transient
    private Long updateBoardId;

    @Column(name="taskOrder")
    private int taskOrder;
}
