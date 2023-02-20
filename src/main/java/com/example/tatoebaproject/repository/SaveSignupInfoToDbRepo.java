package com.example.tatoebaproject.repository;

import com.example.tatoebaproject.security.entity.SignupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveSignupInfoToDbRepo extends JpaRepository<SignupEntity, String> {
     UserDetails findUserByEmail(String userEmail);
}
