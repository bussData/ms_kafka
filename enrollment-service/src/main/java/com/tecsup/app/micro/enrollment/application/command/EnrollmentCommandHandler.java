package com.tecsup.app.micro.enrollment.application.command;

import com.tecsup.app.micro.enrollment.domain.event.LessonCompletedEvent;
import com.tecsup.app.micro.enrollment.domain.event.StudentEnrolledEvent;
import com.tecsup.app.micro.enrollment.domain.model.Enrollment;
import com.tecsup.app.micro.enrollment.domain.repository.EnrollmentRepository;
import com.tecsup.app.micro.enrollment.infrastructure.client.CursoClient;
import com.tecsup.app.micro.enrollment.infrastructure.client.UserClient;
import com.tecsup.app.micro.enrollment.infrastructure.client.dto.CursoDTO;
import com.tecsup.app.micro.enrollment.infrastructure.client.dto.UserDTO;
import com.tecsup.app.micro.enrollment.shared.infrastructure.eventsourcing.MemoryEventStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class EnrollmentCommandHandler {

    private final MemoryEventStore eventStore;

    /**
     * Enrollment to student
     * @param command datos enviado por el controlador
     * @return
     */

    private final UserClient userClient;
    private final CursoClient  cursoClient;
    private final EnrollmentRepository enrollmentRepository;

    /*public EnrollmentCommandHandler(MemoryEventStore eventStore, MemoryEventStore eventStore1, UserClient userClient) {
        this.eventStore = eventStore1;
        this.userClient = userClient;
    }*/

    public String enrollStudent(EnrollStudentCommand command) {

        //validamos usuario:
        UserDTO user = userClient.getUserById(Long.valueOf(command.getStudentId()));
        log.info("Fetching usuarios for user from userdb: {}", user.getFull_name());

        if(command.getStudentName()!=null && user!=null && !user.getFull_name().contains(command.getStudentName())){
            throw new RuntimeException("StudentName invalido. No coincide con "+user.getFull_name());
        }
        if(user==null){
            throw new RuntimeException("StudentId invalido, no existe");
        }

        //validamos curso:
        CursoDTO curso = cursoClient.getCursoById(Long.valueOf(command.getCourseId()));
        log.info("Fetching cursos for curso from coursedb: {}", curso.getTitle());

        if(curso==null){
            throw new RuntimeException("CourseId invalido, no existe");
        }

        Enrollment enroll = Enrollment.create(Long.valueOf(command.getStudentId()), command.getStudentName(),
                Long.valueOf(command.getCourseId()));

        Enrollment saved = enrollmentRepository.save(enroll);
        log.info("Enrollment created: {}", saved.getId());


        // Crear el evento de inscripción
        StudentEnrolledEvent event
                =  StudentEnrolledEvent.builder()
                .enrollmentId(saved.getId())
                .studentId(command.getStudentId())
                .studentName(command.getStudentName())
                .courseId(command.getCourseId())
                .build();

        //
        this.eventStore.save(saved.getId(), event);

        return saved.getId();

    }

    /**
     *
     *
     */
      public void addLesson(String enrollmentId, String lessonId) {

          // 1. Obtener todos los eventos de un enrollment id
          var events = this.eventStore.getEvents(enrollmentId);

          // 2. Reconstruir el estado actual del Enrollemnt : Event Sourcing
          var enrollment = Enrollment.fromEvents(events);

          // 3. Calcular el nuevo progreso . Logica del negocio
           int newProgress =  enrollment.getProgressPercentage() + 10;

           log.info("Adding lesson {} to enrollment {} with progress {} ", lessonId, enrollmentId, newProgress);

          // 4. Crear el nuevo evento para registrar la lesson.
           var eventLesson   = LessonCompletedEvent.builder()
                   .enrollmentId(enrollmentId)
                   .lessonId(lessonId)
                   .newProgressPercentage(newProgress)
                   .build();

           // 5. Almacenar el evento en el Event Store
          this.eventStore.save(enrollmentId, eventLesson);

      }


      public Enrollment getEnrollment(String enrollmentId) {

          // 1. Obtener todos los eventos de un enrollment id
          var events = this.eventStore.getEvents(enrollmentId);

          // 2. Reconstruir el estado actual del Enrollemnt
          var enrollment = Enrollment.fromEvents(events);

          return enrollment;
      }

}
