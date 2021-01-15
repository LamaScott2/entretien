package com.orange.demo.controller;

import com.orange.demo.config.JwtTokenUtil;
import com.orange.demo.config.JwtUserDetails;
import com.orange.demo.config.JwtUserDetailsService;
import com.orange.demo.models.dtos.ResponseDto;
import com.orange.demo.models.dtos.TokenDto;
import com.orange.demo.models.dtos.UserAuthenticateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

@RestController
public class UserController {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/authenticate")
    public ResponseEntity<Object> authenticate(@RequestBody UserAuthenticateDto credentials) {
        try {
            if (credentials.getEmail() == null || credentials.getPassword() == null) {
                return new ResponseEntity<>(new InternalError(), HttpStatus.INTERNAL_SERVER_ERROR);
            }

            JwtUserDetails userDetails = jwtUserDetailsService.loadUserByUsername(credentials.getEmail());
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword(), userDetails.getAuthorities());
            Authentication auth = authenticationManager.authenticate(authentication);
            String token = jwtTokenUtil.generateToken(userDetails);
            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);

            return new ResponseEntity<>(new TokenDto(token), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new InternalError(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/hello")
    public ResponseEntity<Object> hello(Authentication authentication) {
        throw new NotImplementedException();
    }
}
