package com.mapping.controller;

import java.util.List;
import java.util.Optional;

import javax.management.AttributeNotFoundException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.mapping.course.Course;
import com.mapping.course.CourseDto;
import com.mapping.service.CourseService;

@RestController
@RequestMapping("/c")
public class CourseController {
	
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@GetMapping("/courses")
	public ResponseEntity<Course> getAllCourses() {
		
		try {
			
			List<Course> list = courseService.getAllCourse();
			
			if(list.isEmpty())
			{
				return new ResponseEntity("User details are not present..",HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity(list,HttpStatus.FOUND);
			}
			
			
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return new ResponseEntity("This is Bad Request..",HttpStatus.BAD_REQUEST);	
		}	
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Course> getUserById(@PathVariable int id) {
		
		try {
			Optional<Course> course =  courseService.getCourseById(id);
			
			if(course == null)
			{
				return new ResponseEntity("Course is not present by this Id"+id,HttpStatus.NOT_FOUND);
			} else {
				
				CourseDto courseDto = new ModelMapper().map(course, CourseDto.class);
				
				return new ResponseEntity(course,HttpStatus.FOUND);
				
			}
	
		
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("Something went wrong",HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/create")
	public Course createCourse(@RequestBody CourseDto courseDto) throws Exception {
		return courseService.createCourse(courseDto);
	}
	
	
	@PutMapping("/update/{id}")
	public Course updateCourse(@PathVariable int id, @RequestBody CourseDto courseDTO) throws AttributeNotFoundException {
		return courseService.updateCourse(id, courseDTO);
	}
	

	@DeleteMapping("/delete/{id}")
	public void deleteCourse(@PathVariable int id) {
		courseService.deleteCourse(id);
	}
	
	

}
