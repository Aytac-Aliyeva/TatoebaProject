package com.example.tatoebaproject.controller;

import com.example.tatoebaproject.service.DatabaseService;
import com.example.tatoebaproject.service.MessageReceiverServiceImp;
import com.example.tatoebaproject.telegram.update.TelegramResponseDTO;
import com.example.tatoebaproject.telegram.update.TelegramResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private final MessageReceiverServiceImp messageReceiverServiceImp;
    private final DatabaseService databaseService;


    public MyController(MessageReceiverServiceImp messageReceiverService, DatabaseService databaseService) {
        this.messageReceiverServiceImp = messageReceiverService;
        this.databaseService = databaseService;
    }


//    @GetMapping("telegramBot")
//    public ResponseEntity<TelegramResponseDTO> telegram(@RequestParam String from, @RequestParam String to, @RequestParam String text) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(headers);
//        RestTemplate restTemplate = new RestTemplate();
//        TelegramResponseDTO response = restTemplate.exchange("https://api.telegram.org", HttpMethod.GET, requestEntity, TelegramResponseDTO.class).getBody();
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }


    @PostMapping("myTelegram")
    public void getMessage(@RequestBody TelegramResponseDTO telegramResponseDTO) {
        databaseService.saveToDb(telegramResponseDTO);

    }
}


