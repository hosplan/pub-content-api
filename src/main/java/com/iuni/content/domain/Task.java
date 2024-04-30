package com.iuni.content.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name="task")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //제목
    @Column(name="name")
    private String name;
    //내용
    @Column(name="description")
    private String description;

    //시작날짜
    @Column(name="startDate")
    private Date startDate;

    //종료날짜
    @Column(name="endDate")
    private Date endDate;

    //이행 날짜
    @Column(name="dueDate")
    private Date dueDate;

    //점수
    @Column(name="point")
    private int point;

    @ManyToOne
    @JoinColumn(name="statusId")
    @ToString.Exclude
    private Status status;

    @Column(name = "createDate")
    private Date createDate;

    @Column(name = "updateDate")
    private Date updateDate;

    @Column(name = "isShared")
    @ColumnDefault("false")
    private Boolean isShared;

    @ColumnDefault("true")
    private Boolean isLatest;

    @ColumnDefault("false")
    private Boolean isDelete;

    @Transient
    private Long mapId;
    @Transient
    private int taskOrder;
    @Transient
    private String statusName;
    @Transient
    private String creatorNickName;
    @Transient
    private String editorNickName;
    @Transient
    private Long statusId;

    @Column(name="minorVersion")
    @ColumnDefault("0")
    private int minorVersion;

    @Column(name="majorVersion")
    @ColumnDefault("0")
    private int majorVersion;

    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<TaskMemberMap> taskMemberMapList = new ArrayList<>();

    @OneToMany(mappedBy = "task", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();
}
