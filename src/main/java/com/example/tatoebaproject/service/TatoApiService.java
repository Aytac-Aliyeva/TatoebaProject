package com.example.tatoebaproject.service;

import com.example.tatoebaproject.telegram.dto.TatoebaResponse;
import com.example.tatoebaproject.telegram.update.TatoebaObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TatoApiService {


    private final JsoupGenerator jsoupGenerator;

    public TatoApiService(JsoupGenerator jsoupGenerator) {
        this.jsoupGenerator = jsoupGenerator;
    }

    public String jsoup(String word, String from, String to) throws IOException {
        try {
            TatoebaObject tatoebaObject = jsoupGenerator.jsoup(word, from, to);
            String text = tatoebaObject.getText();

            String translation;
            if (tatoebaObject.getTranslations().get(0).size() > 0) {
                translation = tatoebaObject.getTranslations().get(0).get(0).getText();
            } else {
                translation = tatoebaObject.getTranslations().get(1).get(0).getText();
            }


            return TatoebaResponse.builder()
                    .fromLanguage(text)
                    .toLanguage(translation)
                    .build().toString();
        } catch (Exception e) {
            return TatoebaResponse.builder()
                    .fromLanguage("Could not find any result")
                    .toLanguage("")
                    .build().toString();
        }
    }
}




