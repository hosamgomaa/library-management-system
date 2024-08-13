package org.task.maid.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.task.maid.entity.User;
import org.task.maid.repository.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserSeeding implements CommandLineRunner {

    @Value("${seeded-user.username}")
    private String username;

    @Value("${seeded-user.password}")
    private String password;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        Optional<User> userOptional = userRepository.findByUsername("user");
        if (userOptional.isEmpty()) {
            User user = new User();
            user.setUsername(username);
            user.setPassword(passwordEncoder.encode(password));
            userRepository.save(user);
        }
    }
}
