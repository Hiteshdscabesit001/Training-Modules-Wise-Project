package com.mapping.course;

public class CourseDto {
	
	private String cName;
	private String cFee;
	
	
	public CourseDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CourseDto(String cName, String cFee) {
		super();
		this.cName = cName;
		this.cFee = cFee;
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
	@Override
	public String toString() {
		return "CourseDto [cName=" + cName + ", cFee=" + cFee + "]";
	}
	
	

}
