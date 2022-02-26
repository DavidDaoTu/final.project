package com.tudv8.repositories;


import com.tudv8.entities.StudentCourse;
import com.tudv8.entities.StudentCourseId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCourseDAO extends JpaRepository<StudentCourse, StudentCourseId> {
}
