package com.mapping.service;

import java.util.List;
import java.util.Optional;

import javax.management.AttributeNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapping.course.Course;
import com.mapping.course.CourseDto;
import com.mapping.entities.Student;
import com.mapping.entities.StudentDto;
import com.mapping.repo.CourseRepository;

@Service
public class CourseService {
	
	@Autowired
	private CourseRepository courseRepository;
    
	
	// get all course details
	public List<Course> getAllCourse() {
		
		return courseRepository.findAll();	
	}
    
	// get single course details by id
	public Optional<Course> getCourseById(int id) {
		
		return courseRepository.findById(id);
		}

	// create new course
		public Course createCourse(CourseDto courseDto) throws Exception {
			validateCourseDto(courseDto); // Add validation
			Course course = new Course();
			course.setcName(courseDto.getcName());
			course.setcFee(courseDto.getcFee());
			return courseRepository.save(course);
		}

	private void validateCourseDto(CourseDto courseDto) throws Exception {
		
		if (courseDto.getcName() == null || courseDto.getcName().isEmpty() || courseDto.getcFee() == null
				|| courseDto.getcFee().isEmpty()) {
			throw new Exception("Name and Email cannot be empty");
		}
		
	}
    
	// update course details
	public Course updateCourse(int id, CourseDto courseDTO) throws AttributeNotFoundException {
		
		Optional<Course> optionalCourse = courseRepository.findById(id);

		if (optionalCourse.isPresent()) {
			Course course = optionalCourse.get();
			course.setcName(courseDTO.getcName());
			course.setcFee(courseDTO.getcFee());
			return courseRepository.save(course);
		} else {
			throw new AttributeNotFoundException("Student not found with id: " + id);
		}

	}
    
	// delete course details by id
	public void deleteCourse(int id) {
		
		courseRepository.deleteById(id);
		}

		
}
