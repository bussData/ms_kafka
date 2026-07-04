package com.tecsup.app.micro.course.infraestructure.persistence.mapper;

import org.mapstruct.Mapper;
import com.tecsup.app.micro.course.domain.model.Course;
import com.tecsup.app.micro.course.infraestructure.persistence.entity.CourseJpaEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseJpaMapper {

    CourseJpaEntity toEntity(Course course);

    Course toDomain(CourseJpaEntity entity);

    List<Course> toDomain(List<CourseJpaEntity> entities);
}
