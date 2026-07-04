package com.tecsup.app.micro.course.infraestructure.web.dto;

import lombok.Data;

@Data
public class CreateCourseRequest {
    private String title;
    private String description;
}