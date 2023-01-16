package com.example.tatoebaproject.service.schedule;

import com.example.tatoebaproject.service.MessageReceiverService;
import com.example.tatoebaproject.telegram.update.TelegramResponseDTO;
import com.example.tatoebaproject.telegram.update.TelegramUpdateDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Service
public class Scheduler {


    private final MessageReceiverService messageReceiverService;

    public Scheduler(MessageReceiverService messageReceiverService) {
        this.messageReceiverService = messageReceiverService;
    }


    @Scheduled(fixedRateString = "500")
    public void getTelegramUpdates() throws IOException {
        TelegramUpdateDTO telegramUpdateDTO = messageReceiverService.getUpdates();
        if (telegramUpdateDTO != null) {
            String s = telegramUpdateDTO.toString();
            System.out.println(s);

        }
    }
}







