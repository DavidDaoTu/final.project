package com.tudv8.services;

import com.tudv8.entities.Student;
import com.tudv8.repositories.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
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

    public Student getStudentById(Long id) {

        Optional<Student> student = studentDao.findById(id);
        if (student.isPresent()) {
            return student.get();
        } else {
            return null;
        }
    }

    public void saveStudent(Student student) {
        studentDao.save(student);
    }
}
