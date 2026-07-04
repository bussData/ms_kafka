package com.tecsup.app.micro.course.domain.exception;

public class CourseNotFoundException extends RuntimeException {

    public CourseNotFoundException(Long courseId) {
        super("Course not found: " + courseId);
    }
}
