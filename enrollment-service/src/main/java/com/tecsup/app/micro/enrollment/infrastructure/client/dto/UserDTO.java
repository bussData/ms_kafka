package com.tecsup.app.micro.enrollment.infrastructure.client.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String full_name;
    private String email;
    private String status;
}