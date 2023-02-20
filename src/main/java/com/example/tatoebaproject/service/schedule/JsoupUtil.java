package com.example.tatoebaproject.service.schedule;

import com.example.tatoebaproject.service.JsoupGenerator;
import com.example.tatoebaproject.telegram.dto.TatoebaResponse;
import com.example.tatoebaproject.telegram.update.TatoebaObject;
import org.springframework.stereotype.Component;

@Component
public class JsoupUtil {
    private final JsoupGenerator jsoupGenerator;

    public JsoupUtil(JsoupGenerator jsoupGenerator) {
        this.jsoupGenerator = jsoupGenerator;
    }

    public TatoebaResponse jsoupAction(String word, String from, String to) {
        try {
            TatoebaObject tatoeabObject = jsoupGenerator.jsoup(word, from, to);

            String text = tatoeabObject.getText();
//            System.out.println(text);


            String translation = tatoeabObject.getTranslations().get(0).get(0).getText();

            return TatoebaResponse.builder()
                    .fromLanguage(text)
                    .toLanguage(translation)
                    .build();

        } catch (Exception e) {
            return TatoebaResponse.builder()
                    .fromLanguage("Could not find any result")
                    .toLanguage("")
                    .build();
        }
    }
}
