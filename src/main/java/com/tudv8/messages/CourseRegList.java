package com.tudv8.messages;

import java.util.List;

public class CourseRegList {
    private List<Long> idList;

    public CourseRegList() {
    }

    public CourseRegList(List<Long> idList) {
        this.idList = idList;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
}
