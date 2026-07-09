package com.tecsup.app.micro.enrollment.infrastructure.persistence.repository;

import com.tecsup.app.micro.enrollment.domain.model.Enrollment;
import com.tecsup.app.micro.enrollment.infrastructure.client.UserClient;
import com.tecsup.app.micro.enrollment.infrastructure.client.dto.UserDTO;
import com.tecsup.app.micro.enrollment.infrastructure.persistence.entity.EnrollmentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaEnrollmentRepository extends JpaRepository<EnrollmentJpaEntity, Long> {

    List<EnrollmentJpaEntity> findByUserId(Long userId);
    EnrollmentJpaEntity getReferenceById(Long id);
}
