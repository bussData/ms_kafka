package com.tecsup.app.micro.enrollment.domain.event;

import com.tecsup.app.micro.events.DomainEvent;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class StudentEnrolledEvent  extends DomainEvent {

    private final String enrollmentId;
    private final String studentId;
    private final String studentName;
    private final String courseId;

}
