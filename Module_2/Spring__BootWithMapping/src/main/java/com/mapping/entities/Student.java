package com.mapping.entities;

import java.util.List;

import org.hibernate.annotations.Cascade;

import com.mapping.course.Course;

import jakarta.persistence.*;

@Entity
public class Student {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int sId;
	private String sName;
	private String sPhone;
	
	@ManyToMany
	//@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	private List<Course> courses;

	public Student() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Student(int sId, String sName, String sPhone, List<Course> course) {
		super();
		this.sId = sId;
		this.sName = sName;
		this.sPhone = sPhone;
		this.courses = course;
	}

	public int getsId() {
		return sId;
	}

	public void setsId(int sId) {
		this.sId = sId;
	}

	public String getsName() {
		return sName;
	}

	public void setsName(String sName) {
		this.sName = sName;
	}

	public String getsPhone() {
		return sPhone;
	}

	public void setsPhone(String sPhone) {
		this.sPhone = sPhone;
	}

	public List<Course> getCourse() {
		return courses;
	}

	public void setCourse(List<Course> course) {
		this.courses = course;
	}

	@Override
	public String toString() {
		return "Student [sId=" + sId + ", sName=" + sName + ", sPhone=" + sPhone + ", course=" + courses + "]";
	} 
	
	

}
