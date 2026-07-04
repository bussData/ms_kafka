package com.tecsup.app.micro.course.domain.event;

import lombok.Getter;
import lombok.ToString;
import com.tecsup.app.micro.course.domain.event.DomainEvent;

//@AllArgsConstructor
@Getter
@ToString
//@NoArgsConstructor(force = true)
public class CourseCreatedEvent extends DomainEvent {

    private final  String courseId;
    private final String title;

    public CourseCreatedEvent(final String courseId, final String title) {
        this.courseId = courseId;
        this.title = title;
    }

    public CourseCreatedEvent() {
        this.courseId = null;
        this.title = null;
    }


    @Override
    public String getKey() {       // SOBREESCRIBIR EL METODO
        return this.courseId;
    }

}
