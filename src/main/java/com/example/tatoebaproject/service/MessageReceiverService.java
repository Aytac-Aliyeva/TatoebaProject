package com.example.tatoebaproject.service;

import com.example.tatoebaproject.telegram.send.SendMessageResponseDTO;
import com.example.tatoebaproject.telegram.send.text.SendMessageDTO;
import com.example.tatoebaproject.telegram.update.TatoebaObject;
import com.example.tatoebaproject.telegram.update.TelegramResponseDTO;
import com.example.tatoebaproject.telegram.update.TelegramUpdateDTO;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface MessageReceiverService {
    public TelegramUpdateDTO getUpdates() throws IOException;

    public SendMessageResponseDTO sendMessage (SendMessageDTO sendMessageDTO, TatoebaObject tatoebaObject) throws IOException;


}
