package com.example.diningreview.configuration;

import com.example.diningreview.user.Role;
import com.example.diningreview.user.User;
import com.example.diningreview.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    @Autowired
    public DataLoader(UserRepository userRepository,
                      PasswordEncoder encoder) {
        this.userRepo = userRepository;
        this.encoder = encoder;
    }


    @Bean
    public CommandLineRunner createAdmin() {
        return args -> {
            User user = new User();
            user.setPassword(encoder.encode("adminpassword"));
            user.setDisplayName("Admin");
            user.setRoles(List.of(Role.ADMIN.name(), Role.USER.name()));
            user.setCity("None");
            user.setState("None");
            userRepo.save(user);
        };
    }
}
