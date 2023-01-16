package com.example.tatoebaproject.service;

import com.example.tatoebaproject.service.schedule.JsoupUtil;
import com.example.tatoebaproject.telegram.update.TelegramResponseDTO;
import com.example.tatoebaproject.telegram.update.TelegramUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Service
public class MessageReceiverServiceImp implements MessageReceiverService {


    @Value("${base-url}")
    String baseUrl;
    @Value("${api-token}")
    String token;
    @Autowired
    JsoupUtil jsoupUtil;
    private Long offset = null;

    @Override
    public TelegramUpdateDTO getUpdates() throws IOException {

        String url = baseUrl + "/bot" + token + "/getupdates";


        if (offset != null)
            url = url + "?offset=" + offset;
        RestTemplate restTemplate = new RestTemplate();
        TelegramResponseDTO telegramResponseDTO = restTemplate.getForObject(url, TelegramResponseDTO.class);
        if (telegramResponseDTO.getResult().size() > 0) {
            if (telegramResponseDTO.getResult().get(0).getMessageDTO() != null) {
                offset = telegramResponseDTO.getResult().get(0).getUpdateId() + 1;
                jsoupUtil.jsoupAction(telegramResponseDTO.getResult().get(0).getMessageDTO().getText());
                return telegramResponseDTO.getResult().get(0);
            }
        }
        return null;
    }
}
