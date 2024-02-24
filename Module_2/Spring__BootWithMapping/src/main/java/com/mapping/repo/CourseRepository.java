package com.mapping.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mapping.course.Course;
import com.mapping.entities.Student;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	
	List<Course> findAll();

	Course save(Student course);

}
