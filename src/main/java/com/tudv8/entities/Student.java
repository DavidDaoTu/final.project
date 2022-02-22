package com.tudv8.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Student {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "student_name", nullable = false)
    private String studentName;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "birthdate", nullable = false)
    private String birthdate;

    @OneToMany(mappedBy = "student")
    private List<StudentCourse> studentCourses = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student() {
    }

    public Student(String name, String address, String birthdate) {
        this.studentName = name;
        this.address = address;
        this.birthdate = birthdate;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public List<StudentCourse> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(List<StudentCourse> studentCourses) {
        this.studentCourses = studentCourses;
    }

    public void addStudentCourses(StudentCourse studentCourse) {
        this.studentCourses.add(studentCourse);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(studentName, student.studentName) && Objects.equals(address, student.address) && Objects.equals(birthdate, student.birthdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, studentName, address, birthdate);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + studentName + '\'' +
                ", address='" + address + '\'' +
                ", birthdate='" + birthdate + '\'' +
                '}';
    }
}
