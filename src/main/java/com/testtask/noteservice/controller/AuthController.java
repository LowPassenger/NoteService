package com.testtask.noteservice.controller;

import com.testtask.noteservice.config.SecurityConfig;
import com.testtask.noteservice.dto.LoginDto;
import com.testtask.noteservice.model.Role;
import com.testtask.noteservice.model.User;
import com.testtask.noteservice.security.jwt.JwtAuthResponse;
import com.testtask.noteservice.security.jwt.JwtTokenProvider;
import com.testtask.noteservice.service.UserService;
import java.util.Set;
import javax.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@Import(SecurityConfig.class)
@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider tokenProvider;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService,
                          PasswordEncoder passwordEncoder,
                          JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authentication(@Valid @RequestBody LoginDto loginDto) {
        if (!userService.exist(loginDto.getUserName())
                | (!userService.getByName(loginDto.getUserName())
                .getPassword().equals(loginDto.getPassword()))) {
            return new ResponseEntity<>("User not found!", HttpStatus.NOT_FOUND);
        }
        User user = userService.getByName(loginDto.getUserName());
        String token = tokenProvider.generateToken(user);
        log.info("User {} log in", loginDto.getUserName());
        return ResponseEntity.ok(new JwtAuthResponse(token));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody LoginDto loginDto) {
        if (userService.exist(loginDto.getUserName())) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUserName(loginDto.getUserName());
        user.setPassword(loginDto.getPassword());
        user.setRoles(Set.of(Role.USER));
        userService.save(user);
        log.info("User {} successfully registered", user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }
}
