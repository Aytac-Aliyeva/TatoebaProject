package com.example.tatoebaproject.controller;

import com.example.tatoebaproject.service.TatoApiService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class TatoController {
    private final TatoApiService tatoApiService;

    public TatoController(TatoApiService tatoApiService) {
        this.tatoApiService = tatoApiService;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/translate")
    public String translate(@RequestParam("word") String word, @RequestParam("from") String from, @RequestParam("to") String to) throws IOException {
        return tatoApiService.jsoup(word, from, to);

    }

}
