package com.example.tatoebaproject.service;

import com.example.tatoebaproject.telegram.update.TelegramUpdateDTO;
import org.springframework.stereotype.Service;

@Service
public interface MessageReceiverService {
    public TelegramUpdateDTO getUpdates();




}
