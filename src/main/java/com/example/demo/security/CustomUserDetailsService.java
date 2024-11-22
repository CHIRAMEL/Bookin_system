package com.example.demo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import com.example.demo.security.CustomUserDetailsService;


import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if ("user".equals(username)) {
            return new org.springframework.security.core.userdetails.User(
                    "user", // username
                    passwordEncoder.encode("password"), // encode password
                    new ArrayList<>()
            );
        } else if ("admin".equals(username)) {
            return new org.springframework.security.core.userdetails.User(
                    "admin", // username
                    passwordEncoder.encode("admin123"), // encode password
                    new ArrayList<>()
            );
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
