package com.aiportal.student_portal.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import com.aiportal.student_portal.Repositories.EnrollmentRepository;
import com.aiportal.student_portal.Repositories.UserRepository;
import com.aiportal.student_portal.Repositories.CourseRepository;
import com.aiportal.student_portal.Models.Enrollment;
import com.aiportal.student_portal.Models.User;
import com.aiportal.student_portal.Models.Course;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${app.recommender.url}")
    private String recommenderUrl;

    @GetMapping("/{studentId}/enrollments")
    public List<Enrollment> getStudentEnrollments(@PathVariable Long studentId) {
        Optional<User> student = userRepository.findById(studentId);
        if (student.isPresent()) {
            return enrollmentRepository.findByStudent(student.get());
        }
        return List.of();
    }

    @PostMapping("/{studentId}/enroll")
    public ResponseEntity<?> enrollInCourse(@PathVariable Long studentId, @RequestBody Map<String, Long> body) {
        Long courseId = body.get("courseId");

        Optional<User> studentOpt = userRepository.findById(studentId);
        Optional<Course> courseOpt = courseRepository.findById(courseId);

        if (studentOpt.isEmpty() || courseOpt.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Student or course not found"));
        }

        User student = studentOpt.get();
        Course course = courseOpt.get();

       
        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Already enrolled in this course"));
        }

        Enrollment enrollment = new Enrollment(student, course, null);
        enrollmentRepository.save(enrollment);

        return ResponseEntity.ok(Map.of("message", "Enrolled successfully"));
    }

    @GetMapping("/{studentId}/recommendations")
    public ResponseEntity<?> getRecommendations(@PathVariable Long studentId) {
        try {
            String url = recommenderUrl + "?student_id=" + studentId;
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (RestClientException e) {
            // Fallback: return popular courses if recommender is down
            List<Course> popularCourses = courseRepository.findAll().subList(0,
                    Math.min(5, courseRepository.findAll().size()));
            return ResponseEntity.ok(popularCourses);
        }
    }
}
