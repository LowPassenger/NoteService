package com.testtask.noteservice.dto;

import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDto {
    @Size(min = 2, max = 40, message = "User name should be 2...40 characters")
    private String userName;
    @Size(min = 2, max = 50, message = "Password should be 2...50 characters")
    private String password;
}
