package com.tudv8.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Course {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "course_name", nullable = false)
    private String courseName;

    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;

    @Column(name = "end_date", nullable = false)
    private Timestamp endDate;

    @OneToMany(mappedBy = "course")
    private List<StudentCourse> courseStudents = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public List<StudentCourse> getCourseStudents() {
        return courseStudents;
    }

    public void setCourseStudents(List<StudentCourse> courseStudents) {
        this.courseStudents = courseStudents;
    }

    public void addStudentCourse(StudentCourse studentCourse) {
        this.courseStudents.add(studentCourse);
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(id, course.id) && Objects.equals(startDate, course.startDate) && Objects.equals(endDate, course.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, startDate, endDate);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
