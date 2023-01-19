package com.example.tatoebaproject.service;

import com.example.tatoebaproject.service.schedule.JsoupUtil;
import com.example.tatoebaproject.telegram.send.SendMessageResponseDTO;
import com.example.tatoebaproject.telegram.send.text.SendMessageDTO;
import com.example.tatoebaproject.telegram.update.TatoebaObject;
import com.example.tatoebaproject.telegram.update.TelegramResponseDTO;
import com.example.tatoebaproject.telegram.update.TelegramUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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

    @Override
    public SendMessageResponseDTO sendMessage(SendMessageDTO sendMessageDTO, TatoebaObject tatoebaObject) throws IOException {
        String url2 = baseUrl + "/bot" + token + "/sendMessage";

        Long chatId = getUpdates().getUpdateId();
        String text2 = tatoebaObject.getTranslations().get(0).get(0).text;


        Map<Object, Object> request = new HashMap<>();
        request.put("chat_id", chatId);
        request.put("text", text2);
//        request.put("reply_markup", "null");

        RestTemplate restTemplate = new RestTemplate();
        SendMessageResponseDTO sendMessageResponseDTO = restTemplate.postForObject(url2, request, SendMessageResponseDTO.class);
        return sendMessageResponseDTO;
    }
}
