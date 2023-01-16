package com.example.tatoebaproject.service;

import com.example.tatoebaproject.telegram.update.TelegramUpdateDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface MessageReceiverService {
    public TelegramUpdateDTO getUpdates() throws IOException;
}
