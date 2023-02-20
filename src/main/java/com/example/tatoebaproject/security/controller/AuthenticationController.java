package com.example.tatoebaproject.security.controller;

import com.example.tatoebaproject.repository.SaveSignupInfoToDbRepo;
import com.example.tatoebaproject.security.config.JwtUtils;
import com.example.tatoebaproject.security.dto.AuthenticationRequest;
import com.example.tatoebaproject.security.entity.SignupEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final SaveSignupInfoToDbRepo saveSignupInfoToDbRepo;
//    private final UserDao userDao;
//    private final UserDetailsService userDetailsService;


    private final JwtUtils jwtUtils;

//    @PostMapping("/signin")
//    public ResponseEntity<String> signin(@RequestBody AuthenticationRequest request) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
//        final UserDetails userDetails = saveSignupInfoToDbRepo.findUserByEmail(request.getEmail());
//        if (userDetails != null) {
//            return ResponseEntity.ok(jwtUtils.generateToken(userDetails));
//        }
//        return ResponseEntity.status(400).body("Something wrong with token");
//    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        final UserDetails userDetails = saveSignupInfoToDbRepo.findUserByEmail(request.getEmail());
        if (userDetails == null) {
            SignupEntity signupEntity = SignupEntity.builder()
                    .name(request.getEmail())
                    .password(request.getPassword())
                    .role(request.getRole())
                    .build();
            saveSignupInfoToDbRepo.save(signupEntity);
        }
        if (userDetails != null) {
            return ResponseEntity.ok(jwtUtils.generateToken(userDetails));
        }
        return ResponseEntity.status(400).body("Something wrong with token");
    }

}
