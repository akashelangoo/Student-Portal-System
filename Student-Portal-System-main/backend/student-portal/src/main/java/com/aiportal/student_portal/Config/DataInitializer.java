package com.aiportal.student_portal.Config;

import com.aiportal.student_portal.Models.Role;
import com.aiportal.student_portal.Models.User;
import com.aiportal.student_portal.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("test@test.com").isEmpty()) {
            User user = new User();
            user.setEmail("test@test.com");
            user.setPassword(passwordEncoder.encode("password"));
            user.setName("testuser");
            user.setRole(Role.STUDENT);
            userRepository.save(user);
        }
    }
}
