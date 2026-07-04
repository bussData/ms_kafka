package com.tecsup.app.micro.course.application.usecase;

import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.tecsup.app.micro.course.domain.event.CourseCreatedEvent;
import com.tecsup.app.micro.course.domain.model.Course;
import com.tecsup.app.micro.course.domain.repository.CourseRepository;
import com.tecsup.app.micro.course.domain.event.EventPublisher;
import com.tecsup.app.micro.course.infraestructure.event.KafkaEventPublisher;

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
}
