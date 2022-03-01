package com.tudv8.messages;

import com.tudv8.entities.Course;

import java.util.List;

public class TopCourses {
    private Long course_id;
    private String course_name;
    private int score;

    public TopCourses() {
    }

    public TopCourses(Long course_id, String course_name, int score) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.score = score;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public Long getCourse_id() {
        return course_id;
    }

    public void setCourse_id(Long course_id) {
        this.course_id = course_id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
