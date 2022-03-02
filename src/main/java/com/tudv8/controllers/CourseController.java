package com.tudv8.controllers;

import com.tudv8.entities.Course;
import com.tudv8.helper.CSVHelper;
import com.tudv8.messages.CourseInfo;
import com.tudv8.messages.CourseIdsList;
import com.tudv8.messages.ResponseData;
import com.tudv8.services.CourseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {
	@Autowired
	CourseServiceImpl courseServiceImpl;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/courses")
	public List<Course> getCourses() {
		logger.info("Find all courses");
		List<Course> listCourse = null;
		listCourse = courseServiceImpl.getAllCourses();
		return listCourse;
	}

	@GetMapping("/courses/{courseID}")
	public Course getCourse(@PathVariable Long courseID) {
		logger.info("Find a course with given ID: " + courseID);
		Course course = courseServiceImpl.getCourseById(courseID);
		if (course == null) {
			throw new RuntimeException("Can't find the courses with id: " + courseID);
		}
		return course;
	}

	@PostMapping("/courses")
	public ResponseEntity<ResponseData> addCourse(@RequestBody Course theCourse) {
		// just in case, they pass an id in JSON... we need to set id = 0
		// --> Create a new course instead of updating
		logger.info("Add a course with given ID: " + theCourse.getId());
		theCourse.setId(0L);
		return courseServiceImpl.registerCourse(theCourse);
	}

	@PostMapping("/courses/upload")
	public ResponseEntity<ResponseData> uploadFile(@RequestParam("file") MultipartFile file) {
		if (!CSVHelper.isCSVFormat(file)) {
			logger.info("The file is not CSV format");
			return new ResponseEntity<ResponseData>(new ResponseData(-1,	null,"Not a CSV file format"),
																	HttpStatus.OK);
		}
		logger.info("Uploading the file with name: " + file.getName());
		return courseServiceImpl.uploadCSVFileToCourseDB(file);
	}

	@GetMapping("/courses/search")
	public ResponseEntity<ResponseData> findCourses(@RequestParam String courseName) {

		if (courseName == null || courseName.length() == 0) {
			logger.info("Course name is null or empty ");
			ResponseData respData = new ResponseData(-1, null, "Course Name is NULL");
			return new ResponseEntity<>(respData, HttpStatus.OK);
		}
		logger.info("Searching courses by name: " + courseName);
		return courseServiceImpl.findCourseByName(courseName);
	}

	@PutMapping("/course/update")
	public ResponseEntity<ResponseData> updateCourse(@RequestBody CourseInfo courseInfo) {
		logger.info("Updating course information");
		return courseServiceImpl.updateCourseInfo(courseInfo);
	}

	@PostMapping("/courses/delete")
	public ResponseEntity<ResponseData> deleteCourses(@RequestBody CourseIdsList ids) {
		logger.info("Deleting courses' id list: " + ids.getIdList());
		return courseServiceImpl.deleteCourses(ids);
	}

}
