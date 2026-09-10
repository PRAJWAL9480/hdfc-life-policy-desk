package com.hdfc.dto;
import jakarta.validation.constraints.*;

public class EnrollmentRequestDto {
	@NotNull
	@Positive
	private Integer employeeId;
	@NotBlank
	private String employeeName;
	@NotNull
	@Positive
	private Integer courseId;
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public String getEmployeeName() {
		return employeeName;
	}
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	

}
