package com.iuni.content.helper.join;

import java.util.Date;
import java.util.Optional;

public interface JoinTrash {
    Long getMemberId();
    Long getId();
    String getName();

    String getDescription();
    Optional<Date> getEndDate();
    Optional<Date> getStartDate();
    Optional<Date> getDueDate();
    Long getCreator();
    String getCreatorNickName();
    Long getStatusId();

    Long getEditor();
    String getEditorNickName();
//    int getTaskOrder();
    Long getMapId();
    int getPoint();
    Optional<Date> getCreateDate();
    Optional<Date> getUpdateDate();
    int getMinorVersion();
    int getMajorVersion();
    Boolean getIsShared();


    Integer getType();
    Long getTrashId();
}
