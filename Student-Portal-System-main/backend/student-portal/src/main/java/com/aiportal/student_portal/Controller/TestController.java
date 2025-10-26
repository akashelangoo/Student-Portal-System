package com.aiportal.student_portal.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

   
    @GetMapping("/jdbc-connection")
    public String testJdbcConnection() {
        try {
            String result = jdbcTemplate.queryForObject("SELECT '✅ JDBC Connection Successful! MySQL is connected.'", String.class);
            return result;
        } catch (Exception e) {
            return "❌ JDBC Connection Failed: " + e.getMessage();
        }
    }

   
    @GetMapping("/show-tables")
    public List<String> showTables() {
        return jdbcTemplate.queryForList("SHOW TABLES", String.class);
    }

 
    @GetMapping("/insert-sample-data")
    public String insertSampleData() {
        try {
          
            jdbcTemplate.update("INSERT IGNORE INTO users (name, email, password, role) VALUES (?, ?, ?, ?)",
                    "John Doe", "john@student.com", "pass123", "STUDENT");
            jdbcTemplate.update("INSERT IGNORE INTO users (name, email, password, role) VALUES (?, ?, ?, ?)",
                    "Prof. Smith", "smith@faculty.com", "pass123", "FACULTY");

        
            jdbcTemplate.update("INSERT IGNORE INTO course (code, title, description, credits) VALUES (?, ?, ?, ?)",
                    "CS101", "Introduction to Computer Science", "Basic programming concepts", 3);
            jdbcTemplate.update("INSERT IGNORE INTO course (code, title, description, credits) VALUES (?, ?, ?, ?)",
                    "MATH201", "Calculus I", "Limits, derivatives, and integrals", 4);

            return "✅ Sample data inserted successfully!";
        } catch (Exception e) {
            return "❌ Error inserting data: " + e.getMessage();
        }
    }

   
    @GetMapping("/all-users")
    public List<Map<String, Object>> getAllUsers() {
        return jdbcTemplate.queryForList("SELECT * FROM users");
    }

    
    @GetMapping("/all-courses")
    public List<Map<String, Object>> getAllCourses() {
        return jdbcTemplate.queryForList("SELECT * FROM course");
    }

   
    @GetMapping("/user-stats")
    public List<Map<String, Object>> getUserStats() {
        return jdbcTemplate.queryForList(
                "SELECT role, COUNT(*) as count FROM users GROUP BY role"
        );
    }

    @PostMapping("/add-student")
    public String addStudent(@RequestParam String name, @RequestParam String email) {
        try {
            jdbcTemplate.update("INSERT INTO users (name, email, password, role) VALUES (?, ?, ?, ?)",
                    name, email, "temp123", "STUDENT");
            return "✅ Student added successfully: " + name;
        } catch (Exception e) {
            return "❌ Error adding student: " + e.getMessage();
        }
    }


    @PostMapping("/update-course")
    public String updateCourse(@RequestParam String code, @RequestParam String newTitle) {
        try {
            int rows = jdbcTemplate.update("UPDATE course SET title = ? WHERE code = ?", newTitle, code);
            return "✅ Course updated. Affected rows: " + rows;
        } catch (Exception e) {
            return "❌ Error updating course: " + e.getMessage();
        }
    }

  
    @PostMapping("/delete-user")
    public String deleteUser(@RequestParam String email) {
        try {
            int rows = jdbcTemplate.update("DELETE FROM users WHERE email = ?", email);
            return "✅ User deleted. Affected rows: " + rows;
        } catch (Exception e) {
            return "❌ Error deleting user: " + e.getMessage();
        }
    }

  
    @GetMapping("/enrollment-demo")
    public String createEnrollmentDemo() {
        try {
            // Create sample enrollment
            jdbcTemplate.update("INSERT IGNORE INTO enrollment (student_id, course_id, grade) VALUES (1, 1, 85.5)");
            jdbcTemplate.update("INSERT IGNORE INTO enrollment (student_id, course_id, grade) VALUES (1, 2, 92.0)");

            return "✅ Sample enrollments created!";
        } catch (Exception e) {
            return "❌ Error creating enrollments: " + e.getMessage();
        }
    }

 
    @GetMapping("/student-courses")
    public List<Map<String, Object>> getStudentCourses() {
        return jdbcTemplate.queryForList(
                "SELECT u.name as student_name, c.title as course_title, e.grade "
                + "FROM enrollment e "
                + "JOIN users u ON e.student_id = u.id "
                + "JOIN course c ON e.course_id = c.id"
        );
    }
}
