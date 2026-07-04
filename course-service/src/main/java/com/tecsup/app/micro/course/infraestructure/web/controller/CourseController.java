package com.tecsup.app.micro.course.infraestructure.web.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.tecsup.app.micro.course.application.usecase.CreateCourseUseCase;
import com.tecsup.app.micro.course.application.usecase.PublishCourseUseCase;
import com.tecsup.app.micro.course.domain.model.Course;
import com.tecsup.app.micro.course.infraestructure.web.dto.CourseResponse;
import com.tecsup.app.micro.course.infraestructure.web.dto.CreateCourseRequest;
import com.tecsup.app.micro.course.infraestructure.web.mapper.CourseResponseMapper;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CreateCourseUseCase createCourseUseCase;
    private final PublishCourseUseCase publishCourseUseCase;
    private final CourseResponseMapper courseResponseMapper;

    @PostMapping
    public ResponseEntity<CourseResponse> createCourse(@RequestBody CreateCourseRequest request) {
        Course course = createCourseUseCase.createCourse(
                request.getTitle(),
                request.getDescription()
        );
        return ResponseEntity.ok(courseResponseMapper.toResponse(course));
    }

    @PutMapping("/{id}/publish")
    public ResponseEntity<CourseResponse> publishCourse(@PathVariable Long id) {
        Course course = publishCourseUseCase.publishCourse(id);
        return ResponseEntity.ok(courseResponseMapper.toResponse(course));
    }

    @GetMapping
    public ResponseEntity<List<CourseResponse>> findAllCourses() {
        List<Course> lstCursos = createCourseUseCase.getAllCourses();
        return ResponseEntity.ok(courseResponseMapper.toResponse(lstCursos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponse> findCourseById(@PathVariable Long id) {
        Course course = createCourseUseCase.getCourseById(id.toString());
        return ResponseEntity.ok(courseResponseMapper.toResponse(course));
    }
}
