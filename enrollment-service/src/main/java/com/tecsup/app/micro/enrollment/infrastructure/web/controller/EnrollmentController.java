package com.tecsup.app.micro.enrollment.infrastructure.web.controller;


import com.tecsup.app.micro.enrollment.application.command.EnrollStudentCommand;
import com.tecsup.app.micro.enrollment.application.command.EnrollmentCommandHandler;
import com.tecsup.app.micro.enrollment.application.query.EnrollmentQueryRepository;
import com.tecsup.app.micro.enrollment.application.query.EnrollmentReadModel;
import com.tecsup.app.micro.enrollment.application.saga.EnrollmentSagaHandler;
import com.tecsup.app.micro.enrollment.domain.event.EnrollmentCreatedEvent;
import com.tecsup.app.micro.enrollment.domain.model.Enrollment;
import com.tecsup.app.micro.enrollment.infrastructure.dto.EnrollmentRequest;
import com.tecsup.app.micro.enrollment.infrastructure.dto.EnrollmentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentCommandHandler enrollmentCommandHandler;

    private final EnrollmentQueryRepository enrollmentQueryRepository;

    // ========================================
    // SAGA
    // ========================================

    private final EnrollmentSagaHandler sagaHandler;

    @PostMapping("/request")
    public ResponseEntity<EnrollmentResponse> requestEnrollment(
            @RequestBody EnrollmentRequest request) {

        // Iniciar saga
        String enrollmentId = this.sagaHandler.requestEnrollment(request.getStudentId(),
                request.getStudentName(),
                request.getCourseId(),
                request.getAmount());

        EnrollmentResponse response = EnrollmentResponse.builder()
                .enrollmentId(enrollmentId)
                .status("PENDING")
                .message("Enrollment request is being processed")
                .build();

        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(response);
    }


    /**
     *  Enroll a student in a course
     */
    @PostMapping
    public ResponseEntity<EnrollmentResponse> enrollStudent(@RequestBody EnrollmentRequest request) {

        EnrollStudentCommand command
                = EnrollStudentCommand.builder()
                .studentId(request.getStudentId())
                .studentName(request.getStudentName())
                .courseId(request.getCourseId())
                .build();

        Enrollment enrolled = enrollmentCommandHandler.enrollStudent(command);

        return ResponseEntity.ok(EnrollmentResponse
                .builder()
                .enrollmentId(enrolled.getId())
                .status(enrolled.getStatus())
                .build());

    }

    /**
     *  Agregar una lesson al curso
     *  Cada lesson agrega un 10% de progreso al curso.
     * @param enrollmentId
     * @param lessonId
     * @return
     */
    @PostMapping("/{enrollmentId}/lessons/{lessonId}")
    public ResponseEntity<Void> addLesson(@PathVariable String enrollmentId,
                                          @PathVariable String lessonId) {

        enrollmentCommandHandler.addLesson(enrollmentId, lessonId);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{enrollmentId}/progress")
    public ResponseEntity<Void> getEnrollmentProgress(@PathVariable String enrollmentId) {
        // Lógica para obtener el progreso de la inscripción

        Enrollment enrollment = enrollmentCommandHandler.getEnrollment(enrollmentId);

        log.info("Enrollment {} - Current progress: {}%",
                enrollmentId, enrollment.getProgressPercentage());

        return ResponseEntity.ok().build();
    }

    // CQRS Implementation

    @GetMapping("/{id}")
    public ResponseEntity<Enrollment> getEnrollment(@PathVariable String id) {

        Enrollment enroll
                = this.enrollmentQueryRepository.findByEnrollmentId2(id);

        return ResponseEntity.ok(enroll);
    }



    @GetMapping
    public ResponseEntity<List<Enrollment>> getEnrollmentsByUser(
            @RequestParam String userId) {

        return ResponseEntity.ok(
                this.enrollmentQueryRepository.getByUserId(userId)
        );
    }
}
