package com.example.tatoebaproject.service;

import com.example.tatoebaproject.telegram.update.TatoebaObject;
import com.example.tatoebaproject.telegram.update.TelegramResponseDTO;
import com.example.tatoebaproject.telegram.update.TelegramUpdateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class MessageReceiverServiceImp implements MessageReceiverService {


    private Long offset = null;

    @Value("${base-url}")
    String baseUrl;

    @Value("${api-token}")
    String token;

    @Override
    public TelegramUpdateDTO getUpdates() {

        String url = baseUrl + "/bot" + token + "/getupdates";


        if (offset != null)
            url = url + "?offset=" + offset;
        RestTemplate restTemplate = new RestTemplate();
        TelegramResponseDTO telegramResponseDTO = restTemplate.getForObject(url, TelegramResponseDTO.class);
        if (telegramResponseDTO.getResult().size() > 0) {
            if (telegramResponseDTO.getResult().get(0).getMessageDTO() != null) {
                offset = telegramResponseDTO.getResult().get(0).getUpdateId() + 1;
                return telegramResponseDTO.getResult().get(0);
            }
        }
        return null;

    }


}
