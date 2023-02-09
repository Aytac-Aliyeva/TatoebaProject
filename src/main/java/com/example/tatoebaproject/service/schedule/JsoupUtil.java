package com.example.tatoebaproject.service.schedule;

import com.example.tatoebaproject.telegram.dto.TatoebaResponse;
import com.example.tatoebaproject.telegram.update.TatoebaObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JsoupUtil {

    public TatoebaResponse jsoupAction(String word, String from, String to) throws IOException {

        String url = "https://tatoeba.org/en/sentences/search?from=" + from + "&query=" + word + "&to=" + to;
        Document document = Jsoup.connect(url).get();
        Elements div = document.select("div");
        String attr = div.attr("ng-init");

        System.out.println(attr);


        String[] string = attr.split("vm.init\\(\\[], ");
        String[] split = string[1].split(", \\[\\{");
        String result = split[0];
//        System.out.println(result);

        TatoebaObject tatoebaObject = new ObjectMapper().readValue(result, TatoebaObject.class);
        String text = tatoebaObject.getText();
        System.out.println(text);

        String translation;
        if (tatoebaObject.getTranslations().get(0).size() > 0) {
            translation = tatoebaObject.getTranslations().get(0).get(0).getText();
        } else {
            translation = tatoebaObject.getTranslations().get(1).get(0).getText();
        }


        return TatoebaResponse.builder()
                .fromLanguage(text)
                .toLanguage(translation)
                .build();

    }
}
