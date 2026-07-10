package com.tecsup.app.micro.enrollment.domain.event;

import com.tecsup.app.micro.events.DomainEvent;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class LessonCompletedEvent extends DomainEvent {

    private final String enrollmentId;
    private final String lessonId;
    private final int newProgressPercentage;

}
