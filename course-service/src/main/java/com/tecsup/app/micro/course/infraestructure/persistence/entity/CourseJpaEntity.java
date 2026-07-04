package com.tecsup.app.micro.course.infraestructure.persistence.entity;

import com.tecsup.app.micro.course.domain.model.Course;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String instructor;

    @Enumerated(EnumType.STRING)
    private Course.CourseStatus status;

    private LocalDateTime createdAt;
}
