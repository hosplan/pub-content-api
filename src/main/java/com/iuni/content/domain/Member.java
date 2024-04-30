package com.iuni.content.domain;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="member")
@Getter
@Setter
public class Member {
    @Id
    private Long id;

    private String email;

    private Boolean isSocial;

    private String nickName;

    private String name;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Authority> roles = new ArrayList<>();

    @OneToMany(mappedBy = "member", fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();
}
