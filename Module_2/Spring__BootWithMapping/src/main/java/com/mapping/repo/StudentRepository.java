package com.mapping.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mapping.entities.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

	List<Student> findAll();

	

}
