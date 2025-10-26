package com.aiportal.student_portal.Controller;

import org.springframework.web.bind.annotation.*;
import com.aiportal.student_portal.Repositories.CourseRepository;
import com.aiportal.student_portal.Models.Course;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Course> getCourseById(@PathVariable Long id) {
        return courseRepository.findById(id);
    }

    @PostMapping
    public Course createCourse(@RequestBody Course course) {
        return courseRepository.save(course);
    }

    @GetMapping("/search")
    public List<Course> searchCourses(@RequestParam String title) {
        return courseRepository.findByTitleContainingIgnoreCase(title);
    }
}
