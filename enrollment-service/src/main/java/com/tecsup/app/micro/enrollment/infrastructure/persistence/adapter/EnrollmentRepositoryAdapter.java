package com.tecsup.app.micro.enrollment.infrastructure.persistence.adapter;

import com.tecsup.app.micro.enrollment.domain.model.Enrollment;
import com.tecsup.app.micro.enrollment.domain.repository.EnrollmentRepository;
import com.tecsup.app.micro.enrollment.infrastructure.persistence.entity.EnrollmentJpaEntity;
import com.tecsup.app.micro.enrollment.infrastructure.persistence.mapper.EnrollmentJpaMapper;
import com.tecsup.app.micro.enrollment.infrastructure.persistence.repository.JpaEnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EnrollmentRepositoryAdapter implements EnrollmentRepository {

    private final JpaEnrollmentRepository jpaRepository;
    private final EnrollmentJpaMapper  mapper;

    public Enrollment save(Enrollment enrollment) {
        EnrollmentJpaEntity entity = mapper.toEntity(enrollment);
        EnrollmentJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }
}
