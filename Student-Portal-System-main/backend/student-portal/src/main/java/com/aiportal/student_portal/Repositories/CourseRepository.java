package com.aiportal.student_portal.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.aiportal.student_portal.Models.Course;
import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findByTitleContainingIgnoreCase(String title);

    Optional<Course> findByCode(String code);
}
