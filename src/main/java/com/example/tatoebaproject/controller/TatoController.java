package com.example.tatoebaproject.controller;

import com.example.tatoebaproject.service.TatoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;




@RestController
@RequestMapping("/")
public class TatoController {
   private final TatoService tatoService;

    public TatoController(TatoService tatoService) {
        this.tatoService = tatoService;
    }

    @GetMapping("/")
    public String translate(@RequestParam String from, @RequestParam String to, @RequestParam String text) throws IOException {
        String jsoup = tatoService.jsoup(from, to, text);


        return jsoup;
    }

}
