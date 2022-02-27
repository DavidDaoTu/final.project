package com.tudv8.controllers;

import com.tudv8.entities.Student;
import com.tudv8.messages.CourseIdRegList;
import com.tudv8.messages.ResponseData;
import com.tudv8.services.StudentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {
	@Autowired
	StudentServiceImpl studentServiceImpl;

	@GetMapping("/students")
	public List<Student> getStudents() {
		List<Student> listStud = null;
		listStud = studentServiceImpl.getAllStudents();
		return listStud;
	}

	@GetMapping("/students/{studentID}")
	public Student getStudent(@PathVariable Long studentID) {
		Student stud = studentServiceImpl.getCourseById(studentID);
		if (stud == null) {
			throw new RuntimeException("Can't find the student with id: " + studentID);
		}
		return stud;
	}

	@PostMapping("/students")
	public ResponseEntity<ResponseData> addStudent(@RequestBody Student theStudent) {
		// just in case they pass an id in JSON... set id = 0
		// --> Create new student instead of updating
		theStudent.setId(0L);
		return studentServiceImpl.saveStudent(theStudent);
	}

	@PostMapping("/student/{studentId}/registration")
	public ResponseEntity<ResponseData> registerCourse(@PathVariable Long studentId,
													   @RequestBody CourseIdRegList courseIds)
	{
		return studentServiceImpl.enrollCourses(studentId, courseIds);
	}

	@PostMapping("/student/set-score")
	public ResponseEntity<ResponseData> setScore(@RequestParam Long studentId,
												 @RequestParam Long courseId,
												 @RequestParam int score)
	{
		return studentServiceImpl.setScore(studentId, courseId, score);
	}


}
