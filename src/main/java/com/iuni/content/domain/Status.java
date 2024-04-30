package com.iuni.content.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="status")
@Getter
@Setter
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private String type;

    @JsonIgnore
    @OneToMany(mappedBy = "status", fetch=FetchType.EAGER)
    @ToString.Exclude
    private List<Task> taskList = new ArrayList<Task>();

    @JsonIgnore
    @OneToMany(mappedBy="status", fetch=FetchType.EAGER)
    @ToString.Exclude
    private List<Project> projectList = new ArrayList<Project>();

}
