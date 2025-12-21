package com.hisham.blog.services;

import org.springframework.security.core.userdetails.UserDetails;

public interface AuthenticationService {
    public UserDetails authenticate(String email, String password);
    public String generateToken(UserDetails userDetails);
}
