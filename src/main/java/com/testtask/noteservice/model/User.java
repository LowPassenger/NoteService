package com.testtask.noteservice.model;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Cluster0")
@Getter
@Setter
@AllArgsConstructor
public class User {
    @Id
    private String id;
    @NonNull
    private String userName;
    @NonNull
    private String password;
    private Set<Role> roles;

    public User() {
    }

    @Override
    public String toString() {
        return "User{"
                + "id='" + id + '\''
                + ", name='" + userName + '\''
                + ", password='" + "OK" + '\''
                + ", role=" + roles
                + '}';
    }
}
