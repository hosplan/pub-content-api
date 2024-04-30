package com.iuni.content.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TrashBasket {
    private Long id;

    private Long trashId;

    private String type;

    private Date createDate;
}
