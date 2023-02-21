package com.example.tatoebaproject.security.controller;

import com.example.tatoebaproject.repository.SaveSignupInfoToDbRepo;
//import com.example.tatoebaproject.security.config.JwtUtils;
import com.example.tatoebaproject.security.config.JwtTokenUtil;
import com.example.tatoebaproject.security.dto.AuthenticationRequest;
import com.example.tatoebaproject.security.dto.SignInRequest;
import com.example.tatoebaproject.security.entity.SignupEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder encoder;

    private final SaveSignupInfoToDbRepo saveSignupInfoToDbRepo;

    private final JwtTokenUtil jwtUtils;

    private final UserDetailsService userDetailsService;

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SignupEntity byName = saveSignupInfoToDbRepo.findByName(request.getEmail());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(byName.getName());

        if (userDetails != null) {
            return ResponseEntity.ok(jwtUtils.generateToken(userDetails));
        }
        return ResponseEntity.status(400).body("Something wrong with token");
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AuthenticationRequest request) {

        SignupEntity signupEntity = SignupEntity.builder()
                .name(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        saveSignupInfoToDbRepo.save(signupEntity);
        return ResponseEntity.ok("Successfully saved to database");
    }

}
