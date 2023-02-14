package com.example.tatoebaproject.service;

import com.example.tatoebaproject.telegram.update.TatoebaObject;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class TatoService {
        private final JsoupGenerator jsoupGenerator;

        public TatoService(JsoupGenerator jsoupGenerator) {
                this.jsoupGenerator = jsoupGenerator;
        }

        public String jsoup(String word, String from, String to) throws IOException {
                try {
                        TatoebaObject tatoebaObject = jsoupGenerator.jsoup(word, from, to);

                        String text = tatoebaObject.getText();
                        System.out.println(text);

                        String translation;
                        if (tatoebaObject.getTranslations().get(0).size() > 0) {
                                translation = tatoebaObject.getTranslations().get(0).get(0).getText();
                        } else {
                                translation = tatoebaObject.getTranslations().get(1).get(0).getText();
                        }

                } catch (IOException e) {
                        throw new RuntimeException(e);
                }
                return word;
        }
}


