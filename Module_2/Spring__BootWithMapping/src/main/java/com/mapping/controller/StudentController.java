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
import com.mapping.entities.Student;
import com.mapping.entities.StudentDto;
import com.mapping.service.StudentService;

@RestController
@RequestMapping("/s")
public class StudentController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private ModelMapper modelMapper;

	@SuppressWarnings({ "unchecked", "rawtypes", "rawtypes" })
	@GetMapping("/students")
	public ResponseEntity<Student> getAllStudents() {

		try {

			List<Student> list = studentService.getAllStudents();

			if (list.isEmpty()) {
				return new ResponseEntity("User details are not present..", HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity(list, HttpStatus.FOUND);
			}

		} catch (Exception e) {

			e.printStackTrace();
			return new ResponseEntity("This is Bad Request..", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<Course> getUserById(@PathVariable int id) {

		try {
			Optional<Student> student = studentService.getStudentById(id);

			if (student == null) {
				return new ResponseEntity("Student is not present by this Id" + id, HttpStatus.NOT_FOUND);
			} else {
                
				StudentDto studentDto = new ModelMapper().map(student, StudentDto.class);
				
				return new ResponseEntity(student, HttpStatus.FOUND);

			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("Something went wrong", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/create")
	public Student createStudent(@RequestBody StudentDto studentDto) throws Exception {
		return studentService.createStudent(studentDto);
	}
	
	
	@PutMapping("/update/{id}")
	public Student updateStudent(@PathVariable int id, @RequestBody StudentDto studentDTO) throws AttributeNotFoundException {
		return studentService.updateStudent(id, studentDTO);
	}
	

	@DeleteMapping("/delete/{id}")
	public void deleteStudent(@PathVariable int id) {
		studentService.deleteStudent(id);
	}
	
	// for assigning course to student
	@PutMapping("{sId}/course/{cId}")
	public Student assignCourseToStudent(
			@PathVariable int sId,
			@PathVariable int cId) 
	{
		return studentService.assignCourseToStudent(sId,cId);
	}

}
