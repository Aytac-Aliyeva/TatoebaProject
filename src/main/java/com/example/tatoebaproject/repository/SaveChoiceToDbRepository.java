package com.example.tatoebaproject.repository;

import com.example.tatoebaproject.telegram.entity.SaveChiocesToDBEntity;
import com.example.tatoebaproject.telegram.update.TelegramResponseDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaveChoiceToDbRepository extends JpaRepository<SaveChiocesToDBEntity, Long> {
   SaveChiocesToDBEntity findByChatId(Long chatId);

}
