package com.tudv8.repositories;


import com.tudv8.entities.StudentCourse;
import com.tudv8.entities.StudentCourseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface StudentCourseDAO extends JpaRepository<StudentCourse, StudentCourseId> {
    @Query("select sc from StudentCourse sc where sc.studentCourseId.studentId = ?1")
    List<StudentCourse> findAllByStudentId(Long studentId);

    @Query(value = "SELECT * FROM STUDENT_COURSE WHERE STUDENT_COURSE.STUDENT_ID = ?1 " +
            "ORDER BY STUDENT_COURSE.SCORE DESC", nativeQuery = true)
    List<StudentCourse> findAllByStudentIdOrderByScore(Long studentId);
}
