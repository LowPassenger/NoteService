package com.testtask.noteservice.service;

import com.testtask.noteservice.model.User;

public interface UserService {
    User save(User user);

    Boolean exist(String userName);

    User getByName(String userName);
}
