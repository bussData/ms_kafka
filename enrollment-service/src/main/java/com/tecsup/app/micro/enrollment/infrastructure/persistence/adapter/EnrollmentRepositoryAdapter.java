package com.tecsup.app.micro.enrollment.infrastructure.persistence.adapter;

import com.tecsup.app.micro.enrollment.domain.model.Enrollment;
import com.tecsup.app.micro.enrollment.domain.repository.EnrollmentRepository;
import com.tecsup.app.micro.enrollment.infrastructure.client.UserClient;
import com.tecsup.app.micro.enrollment.infrastructure.client.dto.UserDTO;
import com.tecsup.app.micro.enrollment.infrastructure.persistence.entity.EnrollmentJpaEntity;
import com.tecsup.app.micro.enrollment.infrastructure.persistence.mapper.EnrollmentJpaMapper;
import com.tecsup.app.micro.enrollment.infrastructure.persistence.repository.JpaEnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EnrollmentRepositoryAdapter implements EnrollmentRepository {

    private final JpaEnrollmentRepository jpaRepository;
    private final EnrollmentJpaMapper  mapper;
    private final UserClient userClient;
    public Enrollment save(Enrollment enrollment) {
        EnrollmentJpaEntity entity = mapper.toEntity(enrollment);
        EnrollmentJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public List<Enrollment> getEnrollmentByUserId(String studentId) {

        // Si necesitas validar que el usuario exista
        //UserDTO user = userClient.getUserById(Long.valueOf(studentId));

        List<EnrollmentJpaEntity> entities =
                jpaRepository.findByUserId(Long.valueOf(studentId));

        return entities.stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public Enrollment getEnrollmentById(String enrollmentId) {

        EnrollmentJpaEntity entity =
                jpaRepository.getReferenceById(Long.valueOf(enrollmentId));
        return mapper.toDomain(entity);
    }
}

