package com.mapping.service;

import java.util.List;
import java.util.Optional;

import javax.management.AttributeNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mapping.course.Course;
import com.mapping.entities.Student;
import com.mapping.entities.StudentDto;
import com.mapping.repo.CourseRepository;
import com.mapping.repo.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private CourseRepository courseRepository;
    
	// get all students details
	public List<Student> getAllStudents() 
	{
	 return studentRepository.findAll();	
	}
    
	// get single course details by id
	public Optional<Student> getStudentById(int id) {
		
		return studentRepository.findById(id);
		}
    
	// create new student
	public Student createStudent(StudentDto studentDto) throws Exception {
		validateStudentDto(studentDto); // Add validation
		Student student = new Student();
		student.setsName(studentDto.getsName());
		student.setsPhone(studentDto.getsPhone());
		return studentRepository.save(student);
	}
    
	// validation
	private void validateStudentDto(StudentDto studentDto) throws Exception {
		
		if (studentDto.getsName() == null || studentDto.getsName().isEmpty() || studentDto.getsPhone() == null
				|| studentDto.getsPhone().isEmpty()) {
			throw new Exception("Name and Email cannot be empty");
		}
	}
    
	// update student details
	public Student updateStudent(int id, StudentDto studentDTO) throws AttributeNotFoundException {
		
		Optional<Student> optionalStudent = studentRepository.findById(id);

		if (optionalStudent.isPresent()) {
			Student student = optionalStudent.get();
			student.setsName(studentDTO.getsName());
			student.setsPhone(studentDTO.getsPhone());
			return studentRepository.save(student);
		} else {
			throw new AttributeNotFoundException("Student not found with id: " + id);
		}
	}
    
	// delete student details by id
	public void deleteStudent(int id) {
		
		studentRepository.deleteById(id);
		}

	public Student assignCourseToStudent(int sId, int cId) {
		
		List<Course> courselist = null;
		
		Student student = studentRepository.findById(sId).get();
		Course course = courseRepository.findById(cId).get();
		courselist = student.getCourse();
		courselist.add(course);
		student.setCourse(courselist);
		return studentRepository.save(student);
		
	}
	
		
	 
	
	

}
