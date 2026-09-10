package com.hdfc.repository;
import java.util.*;
import org.springframework.stereotype.Repository;

import com.hdfc.entity.Course;
@Repository

public class CourseRepository {
	private Map<Integer,Course>courses=new HashMap<>();
	public Course save(Course course) {
		courses.put(course.getCourseId(),course);
		return course;
	}
	public Course findById(Integer courseId) {
		return courses.get(courseId);
	}
	public Map<Integer,Course>findAll(){
		return courses;
	}
	public void deleteById(Integer courseId) {
		courses.remove(courseId);
	}

}
