package com.hdfc.repository;
import org.springframework.stereotype.Repository;
import com.hdfc.entity.Enrollment;
import java.util.*;
@Repository

public class EnrollmentRepository {
	private Map<Integer,Enrollment> enrollments=new HashMap<>();
	public Enrollment save(Enrollment enrollment) {
		enrollments.put(enrollment.getEnrollmentId(),enrollment);
		return enrollment;
	}
	public Enrollment findById(Integer enrollmentId) {
		return enrollments.get(enrollmentId);
	}
	public Map<Integer,Enrollment>findAll(){return enrollments;

}
	public void deleteById(Integer enrollmentId) {
		enrollments.remove(enrollmentId);
	}}
