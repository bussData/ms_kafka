package com.tecsup.app.micro.course.infraestructure.persistence.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.tecsup.app.micro.course.infraestructure.persistence.entity.CourseJpaEntity;

public interface JpaCourseRepository extends JpaRepository<CourseJpaEntity, Long> {
}
