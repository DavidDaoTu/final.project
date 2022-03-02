package com.tudv8.messages;

import java.util.List;

public class CourseIdsList {
    private List<Long> idList;

    public CourseIdsList() {
    }

    public CourseIdsList(List<Long> idList) {
        this.idList = idList;
    }

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
}
