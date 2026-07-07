package com.tecsup.app.micro.enrollment.infrastructure.client;

import com.tecsup.app.micro.enrollment.infrastructure.client.dto.UserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserClient {

    private final RestTemplate restTemplate;

    @Value("${user.service.url}")
    private String userServiceUrl;

    public UserDTO getUserById(Long id) {
        log.info("Calling User Service (PostgreSQL userdb) to get user with id: {}", id);

        String url = this.userServiceUrl + "/api/users/" + id;
        try {
            UserDTO user = restTemplate.getForObject(url, UserDTO.class);
            log.info("User retrieved successfully from userdb: {}", user);
            return user;
        } catch (Exception e) {
            log.error("Error calling User Service: {}", e.getMessage());
            throw new RuntimeException("Error calling User Service: " + e.getMessage());
        }
    }
}
