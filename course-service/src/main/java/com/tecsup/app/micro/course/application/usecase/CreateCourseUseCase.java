package com.tecsup.app.micro.course.application.usecase;

import com.tecsup.app.micro.course.domain.exception.CourseNotFoundException;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.tecsup.app.micro.course.domain.event.CourseCreatedEvent;
import com.tecsup.app.micro.course.domain.model.Course;
import com.tecsup.app.micro.course.domain.repository.CourseRepository;
import com.tecsup.app.micro.course.domain.event.EventPublisher;
import com.tecsup.app.micro.course.infraestructure.event.KafkaEventPublisher;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateCourseUseCase  {

    private final CourseRepository repository;

    //private final EventPublisher eventPublisher;
    private final KafkaEventPublisher eventPublisher;


    //@Override
    public Course createCourse(String title, String description) {
        Course course = Course.create(title, description);
        Course saved = repository.save(course);
        log.info("Course created: {}", saved.getId());

        // Crear el evento
        CourseCreatedEvent event =
                new CourseCreatedEvent(
                        saved.getId().toString(),
                        saved.getTitle());

        // Publicar el evento
        this.eventPublisher.publish(event);


        return saved;
    }


    public List<Course> getAllCourses() {
        List<Course> lstCourses = repository.findAll();
        return lstCourses;
    }

    public Course getCourseById(String id) {
        Course course = repository.findById(Long.valueOf(id))
                .orElseThrow(() -> new CourseNotFoundException(Long.valueOf(id)));
        return course;
    }
}
