package com.tecsup.app.micro.course.application.usecase;


import com.tecsup.app.micro.course.domain.event.CoursePublishedEvent;
import com.tecsup.app.micro.course.domain.exception.CourseNotFoundException;
import com.tecsup.app.micro.course.domain.model.Course;
import com.tecsup.app.micro.course.domain.repository.CourseRepository;
import com.tecsup.app.micro.course.infraestructure.event.KafkaEventPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class PublishCourseUseCase {

    private final CourseRepository repository;

//    private final EventPublisher eventPublisher;
    private final KafkaEventPublisher eventPublisher;

    //@Override
    @Transactional
    public Course publishCourse(Long courseId) {
        Course course = repository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException(courseId));

        course.publish();
        Course saved = repository.save(course);

        log.info("Course published: {}", saved.getId());

        // Crear el eventp
        CoursePublishedEvent event
                = new CoursePublishedEvent(
                                            saved.getId().toString(),
                                            saved.getTitle()
                                            );

        // Publicar el evento
        this.eventPublisher.publish(event);

        return saved;
    }

}
