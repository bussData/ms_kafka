package com.tecsup.app.micro.enrollment.infrastructure.persistence.repository;

import com.tecsup.app.micro.enrollment.infrastructure.persistence.entity.EnrollmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaEnrollmentRepository extends JpaRepository<EnrollmentJpaEntity, Long> {
}
