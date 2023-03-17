package com.testtask.noteservice.config;

import com.testtask.noteservice.model.Role;
import com.testtask.noteservice.model.User;
import com.testtask.noteservice.repository.UserRepository;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {
    private static final String ADMIN_LOGIN = "admin";
    private static final String ADMIN_PASSWORD = "adminpassword";
    private static final Set<Role> ADMIN_ROLES = Set.of(Role.ADMIN);
    private final UserRepository userRepository;

    @Autowired
    public DataInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void createAdmin() {
        if (!userRepository.existsByUserName(ADMIN_LOGIN)) {
            User user = new User();
            user.setUserName(ADMIN_LOGIN);
            user.setPassword(ADMIN_PASSWORD);
            user.setRoles(ADMIN_ROLES);
            userRepository.save(user);
        }
    }
}
