package com.testtask.TaskManagementSystem.service.impl;

import com.testtask.TaskManagementSystem.DTO.JwtRequest;
import com.testtask.TaskManagementSystem.DTO.JwtResponse;
import com.testtask.TaskManagementSystem.DTO.Register;
import com.testtask.TaskManagementSystem.config.JwtTokenUtil;
import com.testtask.TaskManagementSystem.service.AuthService;
import com.testtask.TaskManagementSystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserServiceImpl userService;
    private final PasswordEncoder encoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;


//    @Override
//    public ResponseEntity<JwtResponse> createToken(JwtRequest jwtRequest) {
//        UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
//        if (!userService.userExists(jwtRequest.getUsername()) || encoder.matches(jwtRequest.getPassword(), userDetails.getPassword())) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        }
//        return ResponseEntity.ok(new JwtResponse(jwtTokenUtil.generateToken(userDetails)));
//    }

    public ResponseEntity<?> createToken(JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @Override
    public boolean register(Register register) {
        if (userService.userExists(register.getUsername())) {
            return false;
        }
        String password = encoder.encode(register.getPassword());
        userService.createUser(register, password);
        return true;
    }
}
