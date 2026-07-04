package com.tecsup.app.micro.course.domain.repository;


import com.tecsup.app.micro.course.domain.model.Course;

import java.util.Optional;

public interface CourseRepository {

    Course save(Course course) ;

    Optional<Course> findById(Long courseId);
}