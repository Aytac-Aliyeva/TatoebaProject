package com.example.tatoebaproject.controller;

import com.example.tatoebaproject.service.MessageReceiverService;
import com.example.tatoebaproject.telegram.update.TelegramUpdateDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    private final MessageReceiverService messageReceiverService;

    public MyController(MessageReceiverService messageReceiverService) {
        this.messageReceiverService = messageReceiverService;
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

//    @GetMapping("/qondarma")
//    public void qondarma(){
//        TelegramUpdateDTO updates = messageReceiverService.getUpdates();


    }


