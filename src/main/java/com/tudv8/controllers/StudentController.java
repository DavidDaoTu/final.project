package com.tudv8.controllers;

import com.tudv8.entities.Student;
import com.tudv8.model.ResponseData;
import com.tudv8.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {
	@Autowired
	StudentService studentService;

	@GetMapping("/students")
	public List<Student> getStudents() {
		List<Student> listStud = null;
		listStud = studentService.getAllStudents();
		return listStud;
	}

	@GetMapping("/students/{studentID}")
	public Student getStudent(@PathVariable Long studentID) {
		Student stud = studentService.getCourseById(studentID);
		if (stud == null) {
			throw new RuntimeException("Can't find the student with id: " + studentID);
		}
		return stud;
	}

	@PostMapping("/students")
	public ResponseEntity<ResponseData> addStudent(@RequestBody Student theStudent) {
		// just in case they pass an id i JSON... set id = 0
		// --> Create new student instead of updating
		theStudent.setId(0L);
		return studentService.saveStudent(theStudent);
	}

}
