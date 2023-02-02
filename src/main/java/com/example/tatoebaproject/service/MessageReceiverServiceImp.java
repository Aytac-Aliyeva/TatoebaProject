package com.example.tatoebaproject.service;

import com.example.tatoebaproject.repository.SaveChoiceToDbRepository;
import com.example.tatoebaproject.repository.TatoRepository;
import com.example.tatoebaproject.service.schedule.JsoupUtil;
import com.example.tatoebaproject.telegram.dto.TatoebaResponse;
import com.example.tatoebaproject.telegram.entity.ChatStage;
import com.example.tatoebaproject.telegram.entity.SaveChiocesToDBEntity;
import com.example.tatoebaproject.telegram.entity.TelegramResponseEntity;
import com.example.tatoebaproject.telegram.send.SendMessageResponseDTO;
import com.example.tatoebaproject.telegram.send.text.SendMessageDTO;
import com.example.tatoebaproject.telegram.update.TelegramResponseDTO;
import com.example.tatoebaproject.telegram.update.TelegramUpdateDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


@Service
public class MessageReceiverServiceImp {

    private final JsoupUtil jsoupUtil;
    private final TatoRepository tatoRepository;
    private final SaveChoiceToDbRepository saveChoiceToDbRepository;


    @Value("${base-url}")
    java.lang.String baseUrl;
    @Value("${api-token}")
    java.lang.String token;
    private Long offset = null;

    public MessageReceiverServiceImp(JsoupUtil jsoupUtil, TatoRepository tatoRepository, SaveChoiceToDbRepository saveChoiceToDbRepository) {
        this.jsoupUtil = jsoupUtil;
        this.tatoRepository = tatoRepository;
        this.saveChoiceToDbRepository = saveChoiceToDbRepository;
    }

    public TelegramUpdateDTO getUpdates() throws IOException {

        java.lang.String url = baseUrl + "/bot" + token + "/getupdates";


        if (offset != null)
            url = url + "?offset=" + offset;
        RestTemplate restTemplate = new RestTemplate();
        TelegramResponseDTO telegramResponseDTO = restTemplate.getForObject(url, TelegramResponseDTO.class);

        if (telegramResponseDTO.getResult().size() > 0) {
            if (telegramResponseDTO.getResult().get(0).getMessageDTO() != null) {
                offset = telegramResponseDTO.getResult().get(0).getUpdateId() + 1;
                saveToDb(telegramResponseDTO);
                saveChoicesToDb(telegramResponseDTO);

                TatoebaResponse text = jsoupUtil.jsoupAction(telegramResponseDTO.getResult().get(0).getMessageDTO().getText());

                Long id = telegramResponseDTO.getResult().get(0).getMessageDTO().getChat().getId();
//                sendMessage(text, id);

                return telegramResponseDTO.getResult().get(0);
            }
        }
        return null;


    }


    public void sendMessage(java.lang.String text, Long id) throws IOException {
        java.lang.String url2 = baseUrl + "/bot" + token + "/sendMessage";


        SendMessageDTO dto = SendMessageDTO.builder()
                .chatId(id)
                .text(text)
                .build();


        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url2, dto, SendMessageResponseDTO.class);

    }


    public void saveToDb(TelegramResponseDTO telegramResponseDTO) {
        java.lang.String text = telegramResponseDTO.getResult().get(0).getMessageDTO().getText();
        TelegramResponseEntity telegramResponseEntity = TelegramResponseEntity.builder()
                .text(text)
                .build();
        tatoRepository.save(telegramResponseEntity);
    }


    public void saveChoicesToDb(TelegramResponseDTO telegramResponseDTO) throws IOException {
        java.lang.String text = telegramResponseDTO.getResult().get(0).getMessageDTO().getText();
        Long id = telegramResponseDTO.getResult().get(0).getMessageDTO().getChat().getId();


        SaveChiocesToDBEntity byChatId = saveChoiceToDbRepository.findByChatId(id);


        if (text.equals("/start")) {
            sendMessage("Salam, Tatoebaya xoş gəldiniz.", id);
        } else if (text.equals("/fromlang")) {
            SaveChiocesToDBEntity saveChiocesToDBEntity = SaveChiocesToDBEntity.builder()
                    .chatId(id)
                    .chatStage(ChatStage.FROM_LANG.name())
                    .build();
            if (byChatId == null) {
                saveChoiceToDbRepository.save(saveChiocesToDBEntity);
            } else {
                saveChoiceToDbRepository.save(byChatId);
            }
            sendMessage("Zəhmət olmasa, hansı dildən tərcümə etmək istədiyinizi seçin.", id);

        } else if (text.equals("/tolang")) {
            SaveChiocesToDBEntity saveChiocesToDBEntity = SaveChiocesToDBEntity.builder()
                    .chatId(id)
                    .chatStage(ChatStage.TO_LANG.name())
                    .build();
            saveChoiceToDbRepository.save(saveChiocesToDBEntity);
            if (byChatId == null) {
                saveChoiceToDbRepository.save(saveChiocesToDBEntity);
            } else {
                saveChoiceToDbRepository.save(byChatId);
            }
            sendMessage("Zəhmət olmasa, hansı dilə tərcümə etmək istədiyinizi seçin.", id);

        }

        if (byChatId != null) {
            java.lang.String chatStage = byChatId.getChatStage();
            if (chatStage.equals("FROM_LANG")) {
                byChatId.setFromLang(text);
                saveChoiceToDbRepository.save(byChatId);
            } else if (chatStage.equals("TO_LANG")) {
                byChatId.setToLang(text);
                saveChoiceToDbRepository.save(byChatId);

            }
        }

    }
}



