package com.tudv8.controllers;

import com.tudv8.entities.Course;
import com.tudv8.model.ResponseData;
import com.tudv8.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {
	@Autowired
	CourseService courseService;

	@GetMapping("/courses")
	public List<Course> getCourses() {
		List<Course> listCourse = null;
		listCourse = courseService.getAllCourses();
		return listCourse;
	}

	@GetMapping("/courses/{courseID}")
	public Course getCourse(@PathVariable Long courseID) {
		Course course = courseService.getCourseById(courseID);
		if (course == null) {
			throw new RuntimeException("Can't find the courses with id: " + courseID);
		}
		return course;
	}

	@PostMapping("/courses")
	public ResponseEntity<ResponseData> addCourse(@RequestBody Course theCourse) {
		// just in case they pass an id i JSON... set id = 0
		// --> Create new student instead of updating
		theCourse.setId(0L);
		return courseService.registerCourse(theCourse);
	}

}
