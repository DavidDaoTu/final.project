package com.tudv8.controllers;

import com.tudv8.entities.Course;
import com.tudv8.helper.CSVHelper;
import com.tudv8.messages.CourseInfo;
import com.tudv8.messages.CourseIdsList;
import com.tudv8.messages.ResponseData;
import com.tudv8.services.CourseServiceImpl;
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

	@GetMapping("/courses")
	public List<Course> getCourses() {
		List<Course> listCourse = null;
		listCourse = courseServiceImpl.getAllCourses();
		return listCourse;
	}

	@GetMapping("/courses/{courseID}")
	public Course getCourse(@PathVariable Long courseID) {
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
		theCourse.setId(0L);
		return courseServiceImpl.registerCourse(theCourse);
	}

	@PostMapping("/courses/upload")
	public ResponseEntity<ResponseData> uploadFile(@RequestParam("file") MultipartFile file) {
		if (!CSVHelper.isCSVFormat(file)) {
			return new ResponseEntity<ResponseData>(new ResponseData(-1,	null,"Not a CSV file format"),
																	HttpStatus.OK);
		}
		return courseServiceImpl.uploadCSVFileToCourseDB(file);
	}

	@GetMapping("/courses/search")
	public ResponseEntity<ResponseData> findCourses(@RequestParam String courseName) {

		if (courseName == null || courseName.length() == 0) {
			ResponseData respData = new ResponseData(-1, null, "Course Name is NULL");
			return new ResponseEntity<>(respData, HttpStatus.OK);
		}
		return courseServiceImpl.findCourseByName(courseName);
	}

	@PutMapping("/course/update")
	public ResponseEntity<ResponseData> updateCourse(@RequestBody CourseInfo courseInfo) {

		return courseServiceImpl.updateCourseInfo(courseInfo);
	}

	@PostMapping("/courses/delete")
	public ResponseEntity<ResponseData> deleteCourses(@RequestBody CourseIdsList ids) {

		return courseServiceImpl.deleteCourses(ids);
	}



}
