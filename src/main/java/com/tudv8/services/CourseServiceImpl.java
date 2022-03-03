package com.tudv8.services;

import com.tudv8.entities.Course;
import com.tudv8.entities.StudentCourse;
import com.tudv8.helper.CSVHelper;
import com.tudv8.messages.CourseIdsList;
import com.tudv8.messages.CourseInfo;
import com.tudv8.messages.ResponseData;
import com.tudv8.repositories.CourseDAO;

import com.tudv8.repositories.StudentCourseDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService{
    @Autowired
    CourseDAO courseDao;

    @Autowired
    StudentCourseDAO studentCourseDAO;

    public ResponseEntity<ResponseData> getAllCourses() {
        ResponseEntity<ResponseData> resObj = null;
        ResponseData respData = null;

        List<Course> courses = courseDao.findAll();
        if (!courses.isEmpty()) {
            List<CourseInfo> coursesInfo = new ArrayList<>();
            for (Course course : courses) {
                coursesInfo.add(new CourseInfo(course.getId(), course.getCourseName(),
                                                course.getStartDate(), course.getEndDate()));
            }
            respData = new ResponseData(0, coursesInfo, "Success to find courses");
        } else {
            respData = new ResponseData(-1, null, "Couldn't find any courses");
        }
        resObj = new ResponseEntity<>(respData, HttpStatus.OK);
        return resObj;
    }

    public Course getCourseById(Long id) {

        Optional<Course> course = courseDao.findById(id);
        if (course.isPresent()) {
            return course.get();
        } else {
            return null;
        }
    }

    public ResponseEntity<ResponseData> registerCourse(CourseInfo course) {
        ResponseEntity<ResponseData> resObj = null;
        ResponseData respData = null;

        if ( course.getStartDate() == null) {
            respData = new ResponseData(-1, course,"Missing Start Date");
        } else if (course.getEndDate() == null) {
            respData = new ResponseData(-1, course, "Missing End Date");
        } else {
            respData = new ResponseData(0,course,"Success to save the course");
            courseDao.save(new Course(course.getName(),
                                        course.getStartDate(),
                                        course.getEndDate()));
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

    public ResponseEntity<ResponseData> findCourseByName(String name) {
        ResponseEntity<ResponseData> resObj = null;
        ResponseData respData;

        List<Course> courses = courseDao.findByCourseNameLikeOrderByCourseNameDesc(name + "%");
        //List<Course> courses = courseDao.findByCourseNameLike(name + "%");

        if (courses.size() > 0 ) {
            respData = new ResponseData(0, courses, "Success to found courses");
        } else {
            respData = new ResponseData(-1, null, "Not Found courses with name: " + name);
        }
        resObj = new ResponseEntity<>(respData, HttpStatus.OK);
        return resObj;
    }

    public ResponseEntity<ResponseData> updateCourseInfo(CourseInfo courseInfo) {
        ResponseEntity<ResponseData> resObj = null;
        ResponseData respData;

        // step1: find the course by id
        Course updatedCourse = findCourseById(courseInfo.getId());
        if (updatedCourse == null) {
            respData = new ResponseData(-1, courseInfo, "Can't find course by info");
        } else {
            // step2: Check whether the course already started
            LocalDateTime currentTime = LocalDateTime.now();
            if ((currentTime.compareTo(updatedCourse.getStartDate().toLocalDateTime()) >= 0) &&
                (currentTime.compareTo(updatedCourse.getEndDate().toLocalDateTime()) <= 0))
            {
                respData = new ResponseData(-2, courseInfo, "The course is already started! Can't changed");
            } else {
                // step3: Update course info
                if (updatedCourse.getStartDate() != null) {
                    updatedCourse.setStartDate(courseInfo.getStartDate());
                }

                if (updatedCourse.getEndDate() != null) {
                    updatedCourse.setEndDate(courseInfo.getEndDate());
                }

                if (updatedCourse.getCourseName() != null) {
                    updatedCourse.setCourseName(courseInfo.getName());
                }

                courseDao.save(updatedCourse); // save updates
                respData = new ResponseData(0, courseInfo, "Success to update course");
            }
        }
        resObj = new ResponseEntity<ResponseData>(respData, HttpStatus.OK);
        return resObj;
    }

    public ResponseEntity<ResponseData> deleteCourses(CourseIdsList ids) {
        ResponseEntity<ResponseData> resObj = null;
        ResponseData respData;

        List<Course> courses = courseDao.findAllById(ids.getIdList());
        LocalDateTime currentTime = LocalDateTime.now();

        List<CourseInfo> deletedCourses = new ArrayList<>();

        if (!courses.isEmpty()) {
            for (Course course : courses) {
                if (currentTime.compareTo(course.getStartDate().toLocalDateTime()) >= 0 &&
                    currentTime.compareTo(course.getEndDate().toLocalDateTime()) <= 0) {
                    continue;
                }

                List<StudentCourse> sc = course.getCourseStudents();
                if (!sc.isEmpty()) {
                    studentCourseDAO.deleteAll(sc); // also delete from STUDENT_COURSE table
                }

                courseDao.delete(course);
                deletedCourses.add(new CourseInfo(course.getId(), course.getCourseName(),
                                                course.getStartDate(), course.getEndDate()));
            }

            if (!deletedCourses.isEmpty()) {
                respData = new ResponseData(0, deletedCourses, "Success to delete below courses " +
                                                                            "& the other courses is invalid to be deleted");
            } else {
                respData = new ResponseData(0, null, "No valid courses deleted");
            }

        } else {
            respData = new ResponseData(-1, null, "No courses found");
        }

        resObj = new ResponseEntity<>(respData, HttpStatus.OK);
        return resObj;
    }

    @Override
    public Course findCourseById(Long id) {
        Optional<Course> courseOptional = courseDao.findById(id);
        if (courseOptional.isPresent()) {
            return courseOptional.get();
        }
        return null;
    }
}
