package com.tecsup.app.micro.enrollment.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CursoDTO {
    private Long id;
    private String title;
    private String description;
    private String published;
}
