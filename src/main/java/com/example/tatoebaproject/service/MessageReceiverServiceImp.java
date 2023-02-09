package com.example.tatoebaproject.service;

import com.example.tatoebaproject.repository.SaveChoiceToDbRepository;
import com.example.tatoebaproject.repository.TatoRepository;
import com.example.tatoebaproject.service.schedule.JsoupUtil;
import com.example.tatoebaproject.telegram.dto.TatoebaResponse;
import com.example.tatoebaproject.telegram.entity.ChatStage;
import com.example.tatoebaproject.telegram.entity.SaveChiocesToDBEntity;
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
    String baseUrl;
    @Value("${api-token}")
    String token;
    private Long offset = null;

    public MessageReceiverServiceImp(JsoupUtil jsoupUtil, TatoRepository tatoRepository, SaveChoiceToDbRepository saveChoiceToDbRepository) {
        this.jsoupUtil = jsoupUtil;
        this.tatoRepository = tatoRepository;
        this.saveChoiceToDbRepository = saveChoiceToDbRepository;
    }

    public TelegramUpdateDTO getUpdates() throws IOException {
        String url = baseUrl + "/bot" + token + "/getupdates";
        if (offset != null)
            url = url + "?offset=" + offset;
        RestTemplate restTemplate = new RestTemplate();
        TelegramResponseDTO telegramResponseDTO = restTemplate.getForObject(url, TelegramResponseDTO.class);

        if (telegramResponseDTO.getResult().size() > 0) {
            if (telegramResponseDTO.getResult().get(0).getMessageDTO() != null) {
                TelegramUpdateDTO telegramUpdateDTO = telegramResponseDTO.getResult().get(0);
                offset = telegramUpdateDTO.getUpdateId() + 1;
                String text = telegramUpdateDTO.getMessageDTO().getText();
                System.out.println(text);
                Long id = telegramUpdateDTO.getMessageDTO().getChat().getId();

                SaveChiocesToDBEntity byChatId = saveChoiceToDbRepository.findByChatId(id);

                Boolean extracted = extracted(text, id, byChatId);

                if (byChatId != null && extracted) {
                    String chatStage = byChatId.getChatStage();
                    if (chatStage.equals(ChatStage.FROM_LANG.name())) {
                        byChatId.setFromLang(text);
                        byChatId.setChatStage(ChatStage.TO_LANG.name());
                        saveChoiceToDbRepository.save(byChatId);
                        sendMessage("Zəhmət olmasa, hansı dilə tərcümə etmək istədiyinizi seçin.", id);
                    } else if (chatStage.equals(ChatStage.TO_LANG.name())) {
                        byChatId.setToLang(text);
                        saveChoiceToDbRepository.save(byChatId);
                        byChatId.setChatStage(ChatStage.COMPLETED.name());
                        sendMessage("Seçiminiz bazaya uğurla yazıldı.", id);
                    } else if (chatStage.equals(ChatStage.COMPLETED.name())) {
                        byChatId.setToLang(text);
                        String fromLang = byChatId.getFromLang();
                        String toLang = byChatId.getToLang();
                        TatoebaResponse tatoebaResponse = jsoupUtil.jsoupAction(text, fromLang, toLang );
                        sendMessage(tatoebaResponse.toString(), id);

                    }
                }
            }
        }
        return null;

    }
    private Boolean extracted(String text, Long id, SaveChiocesToDBEntity byChatId) throws IOException {
        switch (text) {
            case "/start" -> {
                SaveChiocesToDBEntity saveChiocesToDBEntity = SaveChiocesToDBEntity.builder()
                        .chatId(id)
                        .chatStage(ChatStage.FROM_LANG.name())
                        .build();
                if (byChatId == null) {
                    saveChoiceToDbRepository.save(saveChiocesToDBEntity);
                } else {
                    byChatId.setChatStage(ChatStage.FROM_LANG.name());
                    saveChoiceToDbRepository.save(byChatId);
                }
                sendMessage("Salam, Tatoebaya xoş gəldiniz. Zəhmət olmasa hansı dildən tərcümə etmək istədiyinizi seçin", id);
                return false;
            }
            case "/fromlang" -> {
                SaveChiocesToDBEntity saveChiocesToDBEntity = SaveChiocesToDBEntity.builder()
                        .chatId(id)
                        .chatStage(ChatStage.FROM_LANG.name())
                        .build();
                if (byChatId == null) {
                    saveChoiceToDbRepository.save(saveChiocesToDBEntity);
                } else {
                    byChatId.setChatStage(ChatStage.FROM_LANG.name());
                    saveChoiceToDbRepository.save(byChatId);
                }
                sendMessage("Zəhmət olmasa, hansı dildən tərcümə etmək istədiyinizi seçin.", id);
                return false;

            }
            case "/tolang" -> {
                SaveChiocesToDBEntity saveChiocesToDBEntity = SaveChiocesToDBEntity.builder()
                        .chatId(id)
                        .chatStage(ChatStage.TO_LANG.name())
                        .build();
                saveChoiceToDbRepository.save(saveChiocesToDBEntity);
                if (byChatId == null) {
                    saveChoiceToDbRepository.save(saveChiocesToDBEntity);
                } else {
                    byChatId.setChatStage(ChatStage.TO_LANG.name());
                    saveChoiceToDbRepository.save(byChatId);
                }
                sendMessage("Zəhmət olmasa, hansı dilə tərcümə etmək istədiyinizi seçin.", id);


            }
        }
        return true;
    }




    public void sendMessage(String text, Long id) throws IOException {
        String url2 = baseUrl + "/bot" + token + "/sendMessage";
        SendMessageDTO dto = SendMessageDTO.builder()
                .chatId(id)
                .text(text)
                .build();
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(url2, dto, SendMessageResponseDTO.class);

    }


//    public void saveToDb(TelegramResponseDTO telegramResponseDTO) {
//        java.lang.String text = telegramResponseDTO.getResult().get(0).getMessageDTO().getText();
//        TelegramResponseEntity telegramResponseEntity = TelegramResponseEntity.builder()
//                .text(text)
//                .build();
//        tatoRepository.save(telegramResponseEntity);
//    }

//
//    public void saveChoicesToDb(TelegramResponseDTO telegramResponseDTO) throws IOException {
//        String text = telegramResponseDTO.getResult().get(0).getMessageDTO().getText();
//        Long id = telegramResponseDTO.getResult().get(0).getMessageDTO().getChat().getId();
//
//
//    }


}



