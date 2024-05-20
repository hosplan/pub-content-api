package com.iuni.content.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="trash_basket")
public class TrashBasket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long trashId;

    /*type
    0 : task
    1 : board
     */
    private Integer type;

    private Date createDate;

    @Transient
    private String name;
}
