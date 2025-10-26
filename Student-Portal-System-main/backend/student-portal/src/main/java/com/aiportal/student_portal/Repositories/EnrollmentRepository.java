package com.aiportal.student_portal.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aiportal.student_portal.Models.Enrollment;
import com.aiportal.student_portal.Models.User;
import com.aiportal.student_portal.Models.Course;
import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    List<Enrollment> findByStudent(User student);

    List<Enrollment> findByCourse(Course course);

    Optional<Enrollment> findByStudentAndCourse(User student, Course course);

    boolean existsByStudentAndCourse(User student, Course course);
}
