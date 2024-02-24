package com.mapping.course;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mapping.entities.Student;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.*;

@Entity
public class Course {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int cId;
	private String cName;
	private String cFee;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "courses")
	//@ManyToMany(mappedBy = "courses", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Student> student;

	public Course() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Course(int cId, String cName, String cFee, List<Student> student) {
		super();
		this.cId = cId;
		this.cName = cName;
		this.cFee = cFee;
		this.student = student;
	}

	public int getcId() {
		return cId;
	}

	public void setcId(int cId) {
		this.cId = cId;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String getcFee() {
		return cFee;
	}

	public void setcFee(String cFee) {
		this.cFee = cFee;
	}

	public List<Student> getStudent() {
		return student;
	}

	public void setStudent(List<Student> student) {
		this.student = student;
	}

	@Override
	public String toString() {
		return "Course [cId=" + cId + ", cName=" + cName + ", cFee=" + cFee + ", student=" + student + "]";
	}
	
	

}
