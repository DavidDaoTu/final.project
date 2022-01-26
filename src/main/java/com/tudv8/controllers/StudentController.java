package com.tudv8.controllers;

import com.tudv8.entities.Student;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {
	@GetMapping("/students")
	public List<Student> getStudents() {
		return null;
	}
}
