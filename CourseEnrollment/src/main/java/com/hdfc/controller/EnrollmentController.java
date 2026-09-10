package com.hdfc.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hdfc.dto.EnrollmentRequestDto;
import com.hdfc.dto.EnrollmentResponseDto;
import com.hdfc.service.EnrollmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    // Create enrollment
    @PostMapping
    public ResponseEntity<EnrollmentResponseDto> enrollEmployee(
            @Valid @RequestBody EnrollmentRequestDto request) {

        return new ResponseEntity<>(
                enrollmentService.enrollEmployee(request),
                HttpStatus.CREATED);
    }

    // Get all enrollments
    @GetMapping
    public ResponseEntity<List<EnrollmentResponseDto>> getAllEnrollments() {

        return ResponseEntity.ok(
                enrollmentService.getAllEnrollments());
    }

    // Get enrollment by ID
    @GetMapping("/{enrollmentId}")
    public ResponseEntity<EnrollmentResponseDto> getEnrollmentById(
            @PathVariable Integer enrollmentId) {

        return ResponseEntity.ok(
                enrollmentService.getEnrollmentById(enrollmentId));
    }

    // Cancel enrollment
    @PutMapping("/{enrollmentId}/cancel")
    public ResponseEntity<EnrollmentResponseDto> cancelEnrollment(
            @PathVariable Integer enrollmentId) {

        return ResponseEntity.ok(
                enrollmentService.cancelEnrollment(enrollmentId));
    }

    // Complete enrollment
    @PutMapping("/{enrollmentId}/complete")
    public ResponseEntity<EnrollmentResponseDto> completeEnrollment(
            @PathVariable Integer enrollmentId) {

        return ResponseEntity.ok(
                enrollmentService.completeEnrollment(enrollmentId));
    }

    // Get enrollments by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<EnrollmentResponseDto>> getEnrollmentsByStatus(
            @PathVariable String status) {

        return ResponseEntity.ok(
                enrollmentService.getEnrollmentsByStatus(status));
    }

    // Get enrollments by employee
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EnrollmentResponseDto>> getEnrollmentsByEmployeeId(
            @PathVariable Integer employeeId) {

        return ResponseEntity.ok(
                enrollmentService.getEnrollmentsByEmployeeId(employeeId));
    }

    // Get enrollment count
    @GetMapping("/count")
    public ResponseEntity<Long> getEnrollmentCount() {

        return ResponseEntity.ok(
                enrollmentService.getEnrollmentCount());
    }
}