package com.tudv8.services;

import com.tudv8.entities.Course;
import com.tudv8.entities.Student;
import com.tudv8.entities.StudentCourse;
import com.tudv8.entities.StudentCourseId;
import com.tudv8.messages.CourseIdsList;
import com.tudv8.messages.ResponseData;
import com.tudv8.messages.TopCourses;
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

    public ResponseEntity<ResponseData> enrollCourses(Long studentId, CourseIdsList courseIdsList) {
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
        courses = courseDAO.findAllById(courseIdsList.getIdList());

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
        } else {
            respData = new ResponseData(-1, null,
                                "Can't find student with ID: " + studentId);
        }
        respObj = new ResponseEntity<ResponseData>(respData, HttpStatus.OK);
        return respObj;
    }

    public ResponseEntity<ResponseData> setScores(Long studentId,
                                                  List<Long> courseIdsList,
                                                  List<Integer> scores)
    {
        ResponseEntity<ResponseData> respObj = null;
        ResponseData respData = null;

        List<StudentCourse> studentCourses = studentCourseDao.findAllByStudentId(studentId);


        if (studentCourses.size() != 0) {
            int idx = 0;

            for (StudentCourse sc : studentCourses) {
                idx = courseIdsList.indexOf(sc.getCourse().getId());
                if (idx >= 0) {
                    sc.setScore(scores.get(idx));
                }
            }
            studentCourseDao.saveAll(studentCourses);

            respData = new ResponseData(0, null, "Success to set score");
        } else {
            respData = new ResponseData(-1, null,
                    "Student with ID: " + studentId + "not registered any courses");
        }

        respObj = new ResponseEntity<ResponseData>(respData, HttpStatus.OK);
        return respObj;
    }

    public ResponseEntity<ResponseData> getTop10CoursesWithHighScores(Long studentId) {
        ResponseEntity<ResponseData> respObj = null;
        ResponseData respData = null;

        List<StudentCourse> studentCourses = studentCourseDao.findAllByStudentIdOrderByScore(studentId);

        List<TopCourses> topCourses = new ArrayList<>();
        int cnt = 0;

        if (studentCourses.size() > 0) {
            for (StudentCourse sc : studentCourses) {
                cnt++;
                topCourses.add(new TopCourses(sc.getCourse().getId(),
                                                sc.getCourse().getCourseName(),
                                                sc.getScore()));
                if (cnt >= 10) {
                    break;
                }
            }
            respData = new ResponseData(0, topCourses, "Success to find top 10 courses");
        } else {
            respData = new ResponseData(-1, null, "Student not registered courses yet");
        }
        respObj = new ResponseEntity<ResponseData>(respData, HttpStatus.OK);

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
