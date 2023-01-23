package com.example.tatoebaproject.repository;

import com.example.tatoebaproject.telegram.update.TelegramResponseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TatoRepository extends JpaRepository<TelegramResponseEntity, Long> {


}
