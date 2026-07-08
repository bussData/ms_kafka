package com.tecsup.app.micro.enrollment.domain.repository;

import com.tecsup.app.micro.enrollment.domain.model.Enrollment;

import java.util.List;

public interface EnrollmentRepository {

    Enrollment save(Enrollment enrollment);

    List<Enrollment> getEnrollmentByUserId(String studentId);

    Enrollment getEnrollmentById(String enrollmentId);
}
