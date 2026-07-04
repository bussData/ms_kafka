package com.tecsup.app.micro.course.infraestructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import com.tecsup.app.micro.course.domain.model.Course;
import com.tecsup.app.micro.course.domain.repository.CourseRepository;
import com.tecsup.app.micro.course.infraestructure.persistence.entity.CourseJpaEntity;
import com.tecsup.app.micro.course.infraestructure.persistence.mapper.CourseJpaMapper;
import com.tecsup.app.micro.course.infraestructure.persistence.repository.JpaCourseRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CourseRepositoryAdapter implements CourseRepository {

    private final JpaCourseRepository jpaRepository;
    private final CourseJpaMapper mapper;

    @Override
    public Course save(Course course) {
        CourseJpaEntity entity = mapper.toEntity(course);
        CourseJpaEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Course> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Course> findAll() {
        List<CourseJpaEntity> courses = jpaRepository.findAll();
        return mapper.toDomain(courses);
    }
}
