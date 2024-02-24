package com.mapping.entities;

public class StudentDto {
	
	private String sName;
	private String sPhone;
	
	
	public StudentDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public StudentDto(String sName, String sPhone) {
		super();
		this.sName = sName;
		this.sPhone = sPhone;
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
	@Override
	public String toString() {
		return "StudentDto [sName=" + sName + ", sPhone=" + sPhone + "]";
	}
	
	
	

}
