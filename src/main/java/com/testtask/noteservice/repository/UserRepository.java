package com.testtask.noteservice.repository;

import com.testtask.noteservice.model.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUserName(String userName);

    Boolean existsByUserName(String userName);
}
