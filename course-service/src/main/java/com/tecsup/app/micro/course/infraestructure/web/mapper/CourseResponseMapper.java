package com.tecsup.app.micro.course.infraestructure.web.mapper;

import org.mapstruct.Mapper;
import com.tecsup.app.micro.course.domain.model.Course;
import com.tecsup.app.micro.course.infraestructure.web.dto.CourseResponse;

@Mapper(componentModel = "spring")
public interface CourseResponseMapper {

    CourseResponse toResponse(Course course);
}
