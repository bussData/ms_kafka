package com.tecsup.app.micro.enrollment.application.query;

import com.tecsup.app.micro.enrollment.domain.model.Enrollment;
import com.tecsup.app.micro.enrollment.domain.repository.EnrollmentRepository;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class EnrollmentQueryRepository {


    private final EnrollmentRepository enrollmentRepository;

    private final Map<String, EnrollmentReadModel> readModels = new HashMap<>();

    public EnrollmentQueryRepository(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    // Update

    /**
     *
     * @param readModel
     */
    public void save(EnrollmentReadModel readModel) {
        this.readModels.put(readModel.getEnrollmentId(), readModel);
    }

    // Read

    /**
     *
     * @param enrollmentId
     * @return
     */
    public Optional<EnrollmentReadModel> findByEnrollmentId(String enrollmentId) {

        return Optional.ofNullable(this.readModels.get(enrollmentId));

    }

    public Enrollment findByEnrollmentId2(String enrollmentId) {

        return this.enrollmentRepository.getEnrollmentById(enrollmentId);

    }

    /**
     *
     * @return
     */
    public List<EnrollmentReadModel> findAll() {

        return  new ArrayList<>(this.readModels.values());
    }

    public List<EnrollmentReadModel> findByUserId(Long userId) {

        return this.readModels.values()
                .stream()
                .filter(e -> userId.equals(e.getStudentId()))
                .toList();
    }


    public List<Enrollment> getByUserId(String userId) {

        return enrollmentRepository.getEnrollmentByUserId(userId);
    }

}
