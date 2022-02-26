package com.tudv8.services;

import com.tudv8.entities.Student;
import com.tudv8.message.ResponseData;
import com.tudv8.repositories.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    StudentDAO studentDao;

    public List<Student> getAllStudents() {
        return studentDao.findAll();
    }

    public Student getCourseById(Long id) {

        Optional<Student> student = studentDao.findById(id);
        if (student.isPresent()) {
            return student.get();
        } else {
            return null;
        }
    }

    public ResponseEntity<ResponseData> saveStudent(Student student) {
        ResponseEntity<ResponseData> respObj = null;
        ResponseData respData = null;

        if ( student.getStudentName() == null) {
            respData = new ResponseData(-1, student,"Missing Student Name");
        } else if (student.getBirthdate() == null) {
            respData = new ResponseData(-1, student, "Missing Birth Date");
        } else if(student.getAddress() == null) {
            respData = new ResponseData(-1, student, "Missing Address");
        } else {
            respData = new ResponseData(0,student,"Success");
            studentDao.save(student);
        }
        respObj = new ResponseEntity<ResponseData>(respData, HttpStatus.OK);
        return respObj;
    }

}
