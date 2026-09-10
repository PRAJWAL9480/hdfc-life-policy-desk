package com.hdfc.dto;

public class CourseResponseDto {
	private Integer courseId;
	private String courseName;
	private String trainerName;
	private Integer maxCapacity;
	private Double fees;
	private Integer durationInDays;
	public Integer getCourseId() {
		return courseId;
	}
	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getTrainerName() {
		return trainerName;
	}
	public void setTrainerName(String trainerName) {
		this.trainerName = trainerName;
	}
	public Integer getMaxCapacity() {
		return maxCapacity;
	}
	public void setMaxCapacity(Integer maxCapacity) {
		this.maxCapacity = maxCapacity;
	}
	public Double getFees() {
		return fees;
	}
	public void setFees(Double fees) {
		this.fees = fees;
	}
	public Integer getDurationInDays() {
		return durationInDays;
	}
	public void setDurationInDays(Integer durationInDays) {
		this.durationInDays = durationInDays;
	}

}
