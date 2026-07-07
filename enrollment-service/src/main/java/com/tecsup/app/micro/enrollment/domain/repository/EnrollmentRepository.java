package com.tecsup.app.micro.enrollment.domain.repository;

import com.tecsup.app.micro.enrollment.domain.model.Enrollment;

public interface EnrollmentRepository {

    Enrollment save(Enrollment enrollment);

}
