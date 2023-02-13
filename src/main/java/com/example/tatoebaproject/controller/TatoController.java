package com.example.tatoebaproject.controller;

import com.example.tatoebaproject.service.MessageReceiverServiceImp;
import com.example.tatoebaproject.telegram.update.TelegramResponseDTO;
import com.example.tatoebaproject.telegram.update.TelegramUpdateDTO;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class TatoController {

  private final  MessageReceiverServiceImp messageReceiverServiceImp;

    public TatoController(MessageReceiverServiceImp messageReceiverServiceImp) {
        this.messageReceiverServiceImp = messageReceiverServiceImp;
    }
    RestTemplate restTemplate = new RestTemplate();



    @GetMapping("/")
    public ResponseEntity<TelegramResponseDTO> translate(@RequestParam String from, @RequestParam String to, @RequestParam String text) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("X-RapidAPI-Key", "9e1b64bbf4mshe1bacd4370620f2p1f695ejsn22efc21d918b");

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("source", from);
        requestBody.add("target", to);
        requestBody.add("q", text);




        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

//        ResponseEntity<TranslateDto> response =
//                restTemplate.exchange("https://google-translate1.p.rapidapi.com/language/translate/v2",
//                        HttpMethod.POST, request, TranslateDto.class);


        TelegramResponseDTO response = restTemplate.postForObject("https://google-translate1.p.rapidapi.com/language/translate/v2",
                request, TelegramResponseDTO.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
