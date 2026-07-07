package com.tecsup.app.micro.enrollment.domain.model;

import com.tecsup.app.micro.enrollment.domain.event.LessonCompletedEvent;
import com.tecsup.app.micro.enrollment.domain.event.StudentEnrolledEvent;
import com.tecsup.app.micro.enrollment.shared.domain.event.DomainEvent;
import lombok.Getter;
import lombok.Setter;


import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class Enrollment {

    private String id;

    private Long userId;
    private String studentName;
    private Long courseId;
    private String status;

    private int progressPercentage;

    public static Enrollment fromEvents(List<DomainEvent> events) {

        Enrollment enrollment = new Enrollment();

        for (DomainEvent event : events) {
            enrollment.apply(event);
        }
        return enrollment;
    }

    private void apply(DomainEvent event) {

        if (event instanceof StudentEnrolledEvent enrolledEvent) {
            this.id = enrolledEvent.getEnrollmentId();
            this.userId = Long.valueOf(enrolledEvent.getStudentId());
            this.studentName = enrolledEvent.getStudentName();
            this.courseId = Long.valueOf(enrolledEvent.getCourseId());
        } else if (event instanceof  LessonCompletedEvent lessonCompletedEvent) {
            this.progressPercentage = lessonCompletedEvent.getNewProgressPercentage();
        } else if (event instanceof  DomainEvent  domainEvent) {
            // TO DO
        }
    }

    public static Enrollment create(Long studentId, String studentName,
                                     Long courseId) {
        Enrollment enrollment = new Enrollment();
        enrollment.userId = studentId;
        enrollment.studentName = studentName;
        enrollment.courseId = courseId;
        enrollment.status = "PENDING_PAYMENT";
        return enrollment;
    }

}
