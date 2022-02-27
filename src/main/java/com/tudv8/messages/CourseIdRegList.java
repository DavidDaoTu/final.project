package com.tudv8.messages;

import java.util.List;

public class CourseIdRegList {
    private List<Long> idList;

    public CourseIdRegList() {
    }

    public CourseIdRegList(List<Long> idList) {
        this.idList = idList;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
}
