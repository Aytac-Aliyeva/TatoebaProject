package com.example.tatoebaproject.security.controller;

import com.example.tatoebaproject.repository.SaveSignupInfoToDbRepo;
import com.example.tatoebaproject.security.config.JwtTokenUtil;
import com.example.tatoebaproject.security.dto.AuthResponse;
import com.example.tatoebaproject.security.dto.AuthenticationRequest;
import com.example.tatoebaproject.security.dto.SignInRequest;
import com.example.tatoebaproject.security.entity.SignupEntity;
import com.example.tatoebaproject.telegram.dto.TatoebaResponse;
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

import java.util.LinkedList;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder encoder;

    private final SaveSignupInfoToDbRepo saveSignupInfoToDbRepo;

    private final JwtTokenUtil jwtUtils;

    private final UserDetailsService userDetailsService;





    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AuthenticationRequest request) {


        SignupEntity signupEntity = SignupEntity.builder()
                .name(request.getEmail())
                .password(encoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        if (saveSignupInfoToDbRepo.findByName(request.getEmail()) != null) {
            return ResponseEntity.badRequest().body("This user already exists");
        } else saveSignupInfoToDbRepo.save(signupEntity);
        return ResponseEntity.ok("Successfully saved to database");
    }


    @PostMapping("/signin")
    public AuthResponse signin(@RequestBody SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SignupEntity byName = saveSignupInfoToDbRepo.findByName(request.getEmail());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(byName.getName());

        if (userDetails != null) {
            String email = request.getEmail();
            String s = jwtUtils.generateToken(userDetails);

            return AuthResponse.builder()
                    .email(email)
                    .token(s)
                    .build();
        }

        return AuthResponse.builder()
                .email("Something wrong with token")
                .build();
    }

}
