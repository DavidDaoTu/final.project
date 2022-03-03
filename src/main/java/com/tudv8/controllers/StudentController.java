package com.tudv8.controllers;

import com.tudv8.entities.Student;
import com.tudv8.messages.CourseIdsList;
import com.tudv8.messages.ResponseData;
import com.tudv8.messages.CourseScoreList;
import com.tudv8.messages.StudentInfo;
import com.tudv8.services.StudentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentController {
	@Autowired
	StudentServiceImpl studentServiceImpl;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@GetMapping("/students")
	public ResponseEntity<ResponseData> getStudents() {
		logger.info("Find all students");
		return studentServiceImpl.getAllStudents();
	}

	@GetMapping("/students/{studentID}")
	public Student getStudent(@PathVariable Long studentID) {
		logger.info("Find student with given ID in path");
		Student stud = studentServiceImpl.getCourseById(studentID);
		if (stud == null) {
			logger.info("Can't find the student with id: "+ studentID);
			throw new RuntimeException("Can't find the student with id: " + studentID);
		}
		return stud;
	}

	@PostMapping("/students")
	public ResponseEntity<ResponseData> addStudent(@RequestBody StudentInfo theStudent) {
		// just in case they pass an id in JSON... set id = 0
		// --> Create new student instead of updating
		logger.info("Input student information to the database");
		theStudent.setId(0L);
		return studentServiceImpl.saveStudent(theStudent);
	}

	@PostMapping("/student/{studentId}/registration")
	public ResponseEntity<ResponseData> registerCourse(@PathVariable Long studentId,
													   @RequestBody CourseIdsList courseIds)
	{
		logger.info("Student with id: " + studentId +
					"enrolling the course list with ids: " +
					courseIds.getIdList());
		return studentServiceImpl.enrollCourses(studentId, courseIds);
	}

	@PostMapping("/student/set-score")
	public ResponseEntity<ResponseData> setScoreOfCourse(@RequestParam Long studentId,
														 @RequestParam Long courseId,
														 @RequestParam int score)
	{
		logger.info("Setting score of course id: " + courseId + " of student id: " + studentId);
		return studentServiceImpl.setScore(studentId, courseId, score);
	}

	@PostMapping("/student/set-scores")
	public ResponseEntity<ResponseData> setScoresOfCourses(@RequestParam Long studentId,
														   @RequestBody CourseScoreList courseScoreList)
	{
		logger.info("Setting scores of courses' id: " + courseScoreList.getCourseList() +
					" of student id: " + studentId);
		return studentServiceImpl.setScores(studentId,
											courseScoreList.getCourseList(),
											courseScoreList.getScoreList());
	}

	@GetMapping("/student/{studentId}/top-score-courses")
	public ResponseEntity<ResponseData> getTopScoreCourses(@PathVariable Long studentId) {
		logger.info("Get 10 courses with highest scores of student id: " + studentId);
		return studentServiceImpl.getTop10CoursesWithHighScores(studentId);
	}

	// TODO: student should have right to unregister course

}
