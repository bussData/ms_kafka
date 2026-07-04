package com.tecsup.app.micro.course.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    private Long id;
    private String title;
    private String description;
    private String instructor;
    private CourseStatus status;
    private LocalDateTime createdAt;

    public static Course create(String title, String description, String instructor) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title is required");
        }
        if (instructor == null || instructor.isBlank()) {
            throw new IllegalArgumentException("instructor is required");
        }
        Course course = new Course();
        course.title = title;
        course.description = description;
        course.instructor = instructor;
        course.status = CourseStatus.DRAFT;
        course.createdAt = LocalDateTime.now();
        return course;
    }

    public void publish() {
        if (status != CourseStatus.DRAFT) {
            throw new IllegalStateException(
                    "Only DRAFT courses can be published. Current status: " + status);
        }
        this.status = CourseStatus.PUBLISHED;
    }

    public void archive() {
        if (status != CourseStatus.PUBLISHED) {
            throw new IllegalStateException(
                    "Only PUBLISHED courses can be archived. Current status: " + status);
        }
        this.status = CourseStatus.ARCHIVED;
    }

    public enum CourseStatus {
        DRAFT, PUBLISHED, ARCHIVED
    }
}
