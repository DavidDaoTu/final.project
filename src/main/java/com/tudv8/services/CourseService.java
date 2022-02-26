package com.tudv8.services;

import com.tudv8.entities.Course;
import com.tudv8.helper.CSVHelper;
import com.tudv8.message.ResponseData;
import com.tudv8.repositories.CourseDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    CourseDAO courseDao;

    public List<Course> getAllCourses() {
        return courseDao.findAll();
    }

    public Course getCourseById(Long id) {

        Optional<Course> course = courseDao.findById(id);
        if (course.isPresent()) {
            return course.get();
        } else {
            return null;
        }
    }

    public ResponseEntity<ResponseData> registerCourse(Course course) {
        ResponseEntity<ResponseData> resObj = null;
        ResponseData respData = null;

        if ( course.getStartDate() == null) {
            respData = new ResponseData(-1, course,"Missing Start Date");
        } else if (course.getEndDate() == null) {
            respData = new ResponseData(-1, course, "Missing End Date");
        } else {
            respData = new ResponseData(0,course,"Success");
            courseDao.save(course);
        }
        resObj = new ResponseEntity<ResponseData>(respData, HttpStatus.OK);
        return resObj;
    }

    public ResponseEntity<ResponseData> uploadCSVFileToCourseDB(MultipartFile file) {
        ResponseEntity<ResponseData> resObj = null;
        ResponseData respData;

        try {
            List<Course> courses = CSVHelper.convertCsvToCourseList(file.getInputStream());
            courseDao.saveAll(courses);
            respData = new ResponseData(0, courses,"Success to save");
            resObj = new ResponseEntity<ResponseData>(respData, HttpStatus.OK);

        } catch (Exception ex) {
            throw  new RuntimeException("Failed to save csv data to db: " + ex.getMessage());
        }
        return  resObj;
    }

}
