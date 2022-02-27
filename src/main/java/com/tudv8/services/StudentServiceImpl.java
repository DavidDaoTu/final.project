package com.tudv8.services;

import com.tudv8.entities.Course;
import com.tudv8.entities.Student;
import com.tudv8.entities.StudentCourse;
import com.tudv8.entities.StudentCourseId;
import com.tudv8.messages.CourseIdRegList;
import com.tudv8.messages.ResponseData;
import com.tudv8.repositories.CourseDAO;
import com.tudv8.repositories.StudentCourseDAO;
import com.tudv8.repositories.StudentDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements  StudentService{
    @Autowired
    StudentDAO studentDao;

    @Autowired
    CourseDAO courseDAO;

    @Autowired
    CourseService courseService;

    @Autowired
    StudentCourseDAO studentCourseDao;

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

    public ResponseEntity<ResponseData> enrollCourses(Long studentId, CourseIdRegList courseIdList) {
        //TODO: Add checking start_date; set student_course in student & course entity

        ResponseEntity<ResponseData> respObj = null;
        ResponseData respData = null;

        /* Step 1: Find student with given the ID */
        Student student;
        Optional<Student> studentOptional = studentDao.findById(studentId);
        if (!studentOptional.isPresent()) {
            respData = new ResponseData(-1, null, "Can't find student with id = " + studentId);
            return new ResponseEntity<ResponseData>(respData, HttpStatus.OK);
        }
        student = studentOptional.get();

        /* Step 2: Find the course with given the list of IDs  */
        List<Course> courses;
        courses = courseDAO.findAllById(courseIdList.getIdList());

        /* Step 3: Add to StudentCourse */
        List<StudentCourse> studentCourseList = new ArrayList<>();
        for (Course course : courses) {
            StudentCourse studentCourse = new StudentCourse(student, course, 0);
            studentCourseList.add(studentCourse);
            course.setCourseStudents(studentCourseList);

            // courseDAO.save(course); // update student_course of course
        }
        student.setStudentCourses(studentCourseList);
        // studentDao.save(student);

        studentCourseDao.saveAll(studentCourseList);

        respData = new ResponseData(0, courses, "Success to enroll courses");
        respObj = new ResponseEntity<ResponseData>(respData, HttpStatus.OK);

        return respObj;
    }

    public ResponseEntity<ResponseData> setScore(Long studentId, Long courseId, int score)
    {
        ResponseEntity<ResponseData> respObj = null;
        ResponseData respData = null;

        StudentCourseId studentCourseId = new StudentCourseId(studentId, courseId);
        Optional<StudentCourse> studentCourseOptional = studentCourseDao.findById(studentCourseId);
        if (studentCourseOptional.isPresent()) {
            StudentCourse studentCourse = studentCourseOptional.get();
            studentCourse.setScore(score);
            studentCourseDao.save(studentCourse);
            respData = new ResponseData(0, null, "Success to set score");
            respObj = new ResponseEntity<ResponseData>(respData, HttpStatus.OK);
        }

        return respObj;

    }

    @Override
    public Student findStudentById(Long id) {
        Optional<Student> studentOpt = studentDao.findById(id);
        if (studentOpt.isPresent()) {
            return studentOpt.get();
        }
        return null;
    }
}
