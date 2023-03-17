package com.testtask.noteservice.service.impl;

import com.testtask.noteservice.exception.ResourceNotFoundException;
import com.testtask.noteservice.model.Role;
import com.testtask.noteservice.model.User;
import com.testtask.noteservice.repository.UserRepository;
import com.testtask.noteservice.service.UserService;
import java.util.Set;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        User savedUser = new User();
        savedUser.setUserName(user.getUserName());
        savedUser.setPassword(user.getPassword());
        savedUser.setRoles(Set.of(Role.USER));
        log.info("User {} successfully saved to database", savedUser);
        return userRepository.save(savedUser);
    }

    @Override
    public Boolean exist(String userName) {
        return userRepository.existsByUserName(userName);
    }

    @Override
    public User getByName(String userName) {
        return userRepository.findByUserName(userName).orElseThrow(
                () -> new ResourceNotFoundException("User", "userName", userName)
        );
    }
}
