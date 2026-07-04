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
    private boolean published;
    private LocalDateTime createdAt;

    public static Course create(String title, String description) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title is required");
        }

        Course course = new Course();
        course.title = title;
        course.description = description;
        course.published = false;
        course.createdAt = LocalDateTime.now();
        return course;
    }

    public void publish() {
        if (published==true) {
            throw new IllegalStateException(
                    "Only DRAFT courses can be published. Current status: " + published);
        }
        this.published = true;
    }

    /*public void archive() {
        if (!status) {
            throw new IllegalStateException(
                    "Only PUBLISHED courses can be archived. Current status: " + status);
        }
        this.status = CourseStatus.ARCHIVED;
    }*/

    /*public enum CourseStatus {
        DRAFT, PUBLISHED, ARCHIVED
    }*/
}
