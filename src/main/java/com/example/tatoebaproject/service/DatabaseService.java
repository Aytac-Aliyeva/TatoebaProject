package com.example.tatoebaproject.service;

import com.example.tatoebaproject.repository.TatoRepository;
import com.example.tatoebaproject.telegram.update.TelegramResponseDTO;
import com.example.tatoebaproject.telegram.update.TelegramResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {
    private final TatoRepository tatoRepository;

    public DatabaseService(TatoRepository tatoRepository) {
        this.tatoRepository = tatoRepository;
    }

    public void saveToDb(TelegramResponseDTO telegramResponseDTO) {
        String text = telegramResponseDTO.getResult().get(0).getMessageDTO().getText();
        TelegramResponseEntity telegramResponseEntity = TelegramResponseEntity.builder()
                .myText(text)
                .build();
        tatoRepository.save(telegramResponseEntity);
    }
}
