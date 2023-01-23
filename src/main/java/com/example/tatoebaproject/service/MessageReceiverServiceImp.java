package com.example.tatoebaproject.service;

import com.example.tatoebaproject.service.schedule.JsoupUtil;
import com.example.tatoebaproject.telegram.dto.TelegramResponseType;
import com.example.tatoebaproject.telegram.send.SendMessageResponseDTO;
import com.example.tatoebaproject.telegram.send.text.SendMessageDTO;
import com.example.tatoebaproject.telegram.update.TelegramResponseDTO;
import com.example.tatoebaproject.telegram.update.TelegramUpdateDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Service
public class MessageReceiverServiceImp  {


    @Value("${base-url}")
    String baseUrl;
    @Value("${api-token}")
    String token;

    private final JsoupUtil jsoupUtil;


   private final DatabaseService databaseService;

    public MessageReceiverServiceImp(JsoupUtil jsoupUtil, DatabaseService databaseService) {
        this.jsoupUtil = jsoupUtil;
        this.databaseService = databaseService;
    }
    private Long offset = null;


    public TelegramUpdateDTO getUpdates() throws IOException {

        String url = baseUrl + "/bot" + token + "/getupdates";


        if (offset != null)
            url = url + "?offset=" + offset;
        RestTemplate restTemplate = new RestTemplate();
        TelegramResponseDTO telegramResponseDTO = restTemplate.getForObject(url, TelegramResponseDTO.class);

        if (telegramResponseDTO.getResult().size() > 0) {
            if (telegramResponseDTO.getResult().get(0).getMessageDTO() != null) {
                offset = telegramResponseDTO.getResult().get(0).getUpdateId() + 1;
                databaseService.saveToDb(telegramResponseDTO);

                TelegramResponseType text = jsoupUtil.jsoupAction(telegramResponseDTO.getResult().get(0).getMessageDTO().getText());

                Long id = telegramResponseDTO.getResult().get(0).getMessageDTO().getChat().getId();
                sendMessage(text, id);

                return telegramResponseDTO.getResult().get(0);
            }
        }
        return null;
    }


    public SendMessageResponseDTO sendMessage(TelegramResponseType text, Long id  ) throws IOException {
        String url2 = baseUrl + "/bot" + token + "/sendMessage";


        SendMessageDTO dto = SendMessageDTO.builder()
                .chatId(id)
                .text(text.toString())
                .build();


        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url2, dto, SendMessageResponseDTO.class);

        return null;
    }
}
