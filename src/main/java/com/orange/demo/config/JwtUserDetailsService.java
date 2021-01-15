package com.orange.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public JwtUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        switch (email) {
            case "emailAdmin":
                return new JwtUserDetails(email, "passwordAdmin", "ROLE_ADMIN");
            case "emailUser":
                return new JwtUserDetails(email, "passwordUser", "ROLE_USER");
            default:
                throw new UsernameNotFoundException("User not found.");
        }
    }
}