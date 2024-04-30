package com.iuni.content.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name="board")
@Getter
@Setter
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="projectId",nullable = true)
    private Long projectId;

    @Column(name="creator")
    private Long creator;

    @Column(name="boardOrder")
    private int boardOrder;

    @ColumnDefault("false")
    private boolean isDelete;
}
