package com.example.tatoebaproject.service.schedule;

import com.example.tatoebaproject.service.MessageReceiverServiceImp;
import com.example.tatoebaproject.telegram.update.TelegramUpdateDTO;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class Scheduler {


    private final MessageReceiverServiceImp messageReceiverServiceImp;

    public Scheduler(MessageReceiverServiceImp messageReceiverService) {
        this.messageReceiverServiceImp = messageReceiverService;
    }


    @Scheduled(fixedRateString = "500")
    public void getTelegramUpdates() throws IOException {
        TelegramUpdateDTO telegramUpdateDTO = messageReceiverServiceImp.getUpdates();


    }
}







