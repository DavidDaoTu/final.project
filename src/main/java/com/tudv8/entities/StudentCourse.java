package com.tudv8.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class StudentCourse {
    @EmbeddedId
    private StudentCourseId studentCourseId;

    @ManyToOne
    @MapsId("studentId")
    private Student student;

    @ManyToOne
    @MapsId("courseId")
    private Course course;

    @Column(name = "score")
    private int score = 0;

    public StudentCourse() {
    }

    public StudentCourse(Student student, Course course, int score) {
        this.student = student;
        this.course = course;
        this.score = score;
        this.studentCourseId = new StudentCourseId(student.getId(), course.getId());
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "StudentCourse{" +
                "studentCourseId=" + studentCourseId +
                ", student=" + student +
                ", course=" + course +
                ", score=" + score +
                '}';
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentCourse that = (StudentCourse) o;
        return score == that.score && Objects.equals(studentCourseId, that.studentCourseId) && Objects.equals(student, that.student) && Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentCourseId, student, course, score);
    }
}
