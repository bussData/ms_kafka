package com.tecsup.app.micro.enrollment.infrastructure.client;

import com.tecsup.app.micro.enrollment.infrastructure.client.dto.CursoDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
@Slf4j
public class CursoClient {

    private final RestTemplate restTemplate;

    @Value("${curso.service.url}")
    private String cursoServiceUrl;

    public CursoDTO getCursoById(Long id) {
        log.info("Calling User Service (PostgreSQL coursedb) to get curso with id: {}", id);

        String url = this.cursoServiceUrl + "/api/courses/" + id;
        try {
            CursoDTO user = restTemplate.getForObject(url, CursoDTO.class);
            log.info("Curso retrieved successfully from coursedb: {}", user);
            return user;
        } catch (Exception e) {
            log.error("Error calling Curso Service: {}", e.getMessage());
            throw new RuntimeException("Error calling Curso Service: " + e.getMessage());
        }
    }

}
