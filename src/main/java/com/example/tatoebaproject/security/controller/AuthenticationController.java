package com.example.tatoebaproject.security.controller;

import com.example.tatoebaproject.repository.SaveSignupInfoToDbRepo;
import com.example.tatoebaproject.security.config.JwtTokenUtil;
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

    @PostMapping("/signin")
    public LinkedList signin(@RequestBody SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        SignupEntity byName = saveSignupInfoToDbRepo.findByName(request.getEmail());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(byName.getName());

        if (userDetails != null) {
            String email = request.getEmail();
            String password = request.getPassword();
            String s = jwtUtils.generateToken(userDetails);
            LinkedList<Object> linkedList = new LinkedList<>();
            linkedList.add(email);
            linkedList.add(password);
            linkedList.add(s);
        return linkedList;
        }
//        return ResponseEntity.status(400).body("Something wrong with token");
        LinkedList<Object> linkedList = new LinkedList<>();
        linkedList.add("Something wrong with token");

        return linkedList;
    }


//    @PostMapping("/signin")
//    public String signin(@RequestBody SignInRequest request) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
//        SignupEntity byName = saveSignupInfoToDbRepo.findByName(request.getEmail());
//
//        final UserDetails userDetails = userDetailsService
//                .loadUserByUsername(byName.getName());
//
//        if (userDetails != null) {
//            String email = request.getEmail();
//            String password = request.getPassword();
//            String s = jwtUtils.generateToken(userDetails);
//
//            return TatoebaResponse.builder()
//                    .toLanguage(email)
//                    .fromLanguage(password)
//                    .fromLanguage(s)
//                    .build().toString();
//        }
//
//        return TatoebaResponse.builder()
//                .toLanguage("Something wrong with token")
//                .build().toString();
//    }

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

}
