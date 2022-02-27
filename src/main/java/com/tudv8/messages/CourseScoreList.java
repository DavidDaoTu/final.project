package com.tudv8.messages;

import java.util.List;

public class CourseScoreList {
    private List<Integer> scoreList;
    private List<Long> courseList;


    public CourseScoreList() {
    }

    public CourseScoreList(List<Integer> scoreList, List<Long> courseList) {
        this.scoreList = scoreList;
        this.courseList = courseList;
    }

    public List<Integer> getScoreList() {
        return scoreList;
    }

    public void setScoreList(List<Integer> scoreList) {
        this.scoreList = scoreList;
    }

    public List<Long> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Long> courseList) {
        this.courseList = courseList;
    }
}
