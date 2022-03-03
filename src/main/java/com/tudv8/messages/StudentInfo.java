package com.tudv8.messages;

public class StudentInfo {
    private Long id;
    private String studentName;
    private String address;
    private String birthDate;

    public StudentInfo(Long id, String studentName, String address, String birthDate) {
        this.id = id;
        this.studentName = studentName;
        this.address = address;
        this.birthDate = birthDate;
    }

    public StudentInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
}
