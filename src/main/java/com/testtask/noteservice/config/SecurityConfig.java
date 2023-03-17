package com.testtask.noteservice.config;

import com.testtask.noteservice.model.Role;
import com.testtask.noteservice.security.jwt.JwtAuthenticationEntryPoint;
import com.testtask.noteservice.security.jwt.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService,
                          JwtAuthenticationEntryPoint authenticationEntryPoint) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeHttpRequests(
                        (authorize) -> authorize
                                .antMatchers(HttpMethod.POST, "/auth/signin/**").permitAll()
                                .antMatchers(HttpMethod.POST, "/auth/signup/**").permitAll()
                                .antMatchers(HttpMethod.GET, "/notes/likes/**")
                                .hasRole(Role.USER.getName())
                                .antMatchers(HttpMethod.GET, "/notes/likes/**")
                                .hasRole(Role.ADMIN.getName())
                                .antMatchers(HttpMethod.POST, "/notes/create/**").permitAll()
                                .antMatchers(HttpMethod.GET, "/notes/all/**").permitAll()
                                .antMatchers(HttpMethod.PUT, "/notes/**")
                                .hasRole(Role.USER.getName())
                                .antMatchers(HttpMethod.PUT, "/notes/**")
                                .hasRole(Role.ADMIN.getName())
                                .antMatchers(HttpMethod.DELETE, "/notes/**")
                                .hasRole(Role.USER.getName())
                                .antMatchers(HttpMethod.DELETE, "/notes/**")
                                .hasRole(Role.ADMIN.getName())
                                .antMatchers(HttpMethod.POST, "/notes/**")
                                .hasRole(Role.USER.getName())
                                .antMatchers(HttpMethod.POST, "/notes/**")
                                .hasRole(Role.ADMIN.getName())
                                .anyRequest()
                                .authenticated());
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
