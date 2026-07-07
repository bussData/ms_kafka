package com.tecsup.app.micro.enrollment.infrastructure.persistence.mapper;

import com.tecsup.app.micro.enrollment.domain.model.Enrollment;
import com.tecsup.app.micro.enrollment.infrastructure.persistence.entity.EnrollmentJpaEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnrollmentJpaMapper {
    EnrollmentJpaEntity toEntity(Enrollment enrollment);

    Enrollment toDomain(EnrollmentJpaEntity entity);
}
