package com.hdfc.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hdfc.dto.EnrollmentRequestDto;
import com.hdfc.dto.EnrollmentResponseDto;
import com.hdfc.entity.Course;
import com.hdfc.entity.Enrollment;
import com.hdfc.exception.CourseCapacityFullException;
import com.hdfc.exception.CourseNotFoundException;
import com.hdfc.exception.DuplicateEnrollmentException;
import com.hdfc.exception.EnrollmentNotFoundException;
import com.hdfc.mapper.EnrollmentMapper;
import com.hdfc.repository.CourseRepository;
import com.hdfc.repository.EnrollmentRepository;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentServiceImpl(
            EnrollmentRepository enrollmentRepository,
            CourseRepository courseRepository) {

        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public EnrollmentResponseDto enrollEmployee(
            EnrollmentRequestDto request) {

        // Rule 1: Check whether course exists
        Course course = courseRepository.findById(request.getCourseId());

        if (course == null) {
            throw new CourseNotFoundException(
                    "Course not found with id: " + request.getCourseId());
        }

        // Rule 2: Check course capacity
        long enrolledCount = enrollmentRepository.findAll()
                .values()
                .stream()
                .filter(enrollment ->
                        enrollment.getCourseId().equals(request.getCourseId()))
                .filter(enrollment ->
                        "ENROLLED".equals(enrollment.getStatus()))
                .count();

        if (enrolledCount >= course.getMaxCapacity()) {
            throw new CourseCapacityFullException(
                    "Course capacity is full for course id: "
                            + request.getCourseId());
        }

        // Rule 3: Check duplicate enrollment
        boolean alreadyEnrolled = enrollmentRepository.findAll()
                .values()
                .stream()
                .anyMatch(enrollment ->
                        enrollment.getEmployeeId()
                                .equals(request.getEmployeeId())
                        && enrollment.getCourseId()
                                .equals(request.getCourseId()));

        if (alreadyEnrolled) {
            throw new DuplicateEnrollmentException(
                    "Employee is already enrolled in this course");
        }

        // Create enrollment
        Enrollment enrollment = EnrollmentMapper.toEntity(request);

        enrollment.setEnrollmentId(generateEnrollmentId());
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setStatus("ENROLLED");

        Enrollment savedEnrollment =
                enrollmentRepository.save(enrollment);

        return EnrollmentMapper.toResponseDto(savedEnrollment);
    }

    @Override
    public List<EnrollmentResponseDto> getAllEnrollments() {

        return enrollmentRepository.findAll()
                .values()
                .stream()
                .map(EnrollmentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public EnrollmentResponseDto getEnrollmentById(
            Integer enrollmentId) {

        Enrollment enrollment =
                enrollmentRepository.findById(enrollmentId);

        if (enrollment == null) {
            throw new EnrollmentNotFoundException(
                    "Enrollment not found with id: " + enrollmentId);
        }

        return EnrollmentMapper.toResponseDto(enrollment);
    }

    @Override
    public EnrollmentResponseDto cancelEnrollment(
            Integer enrollmentId) {

        Enrollment enrollment =
                enrollmentRepository.findById(enrollmentId);

        if (enrollment == null) {
            throw new EnrollmentNotFoundException(
                    "Enrollment not found with id: " + enrollmentId);
        }

        enrollment.setStatus("CANCELLED");

        enrollmentRepository.save(enrollment);

        return EnrollmentMapper.toResponseDto(enrollment);
    }

    @Override
    public EnrollmentResponseDto completeEnrollment(
            Integer enrollmentId) {

        Enrollment enrollment =
                enrollmentRepository.findById(enrollmentId);

        if (enrollment == null) {
            throw new EnrollmentNotFoundException(
                    "Enrollment not found with id: " + enrollmentId);
        }

        enrollment.setStatus("COMPLETED");

        enrollmentRepository.save(enrollment);

        return EnrollmentMapper.toResponseDto(enrollment);
    }

    @Override
    public List<EnrollmentResponseDto> getEnrollmentsByStatus(
            String status) {

        return enrollmentRepository.findAll()
                .values()
                .stream()
                .filter(enrollment ->
                        enrollment.getStatus()
                                .equalsIgnoreCase(status))
                .map(EnrollmentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EnrollmentResponseDto> getEnrollmentsByEmployeeId(
            Integer employeeId) {

        return enrollmentRepository.findAll()
                .values()
                .stream()
                .filter(enrollment ->
                        enrollment.getEmployeeId().equals(employeeId))
                .map(EnrollmentMapper::toResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public long getEnrollmentCount() {

        return enrollmentRepository.findAll()
                .values()
                .stream()
                .count();
    }

    private Integer generateEnrollmentId() {

        if (enrollmentRepository.findAll().isEmpty()) {
            return 1;
        }

        return enrollmentRepository.findAll()
                .keySet()
                .stream()
                .max(Integer::compareTo)
                .get() + 1;
    }
}