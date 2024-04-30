package com.iuni.content.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="task_map")
@Getter
@Setter
public class TaskMap {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "start_id")
    @ToString.Exclude
    private Task startTask;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "end_id")
    @ToString.Exclude
    private Task endTask;


}
