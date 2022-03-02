package com.tudv8.repositories;

import com.tudv8.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseDAO extends JpaRepository<Course, Long> {
    List<Course> findByCourseNameLikeOrderByCourseNameDesc(String name);
    List<Course> findByCourseNameLike(String name);

}
