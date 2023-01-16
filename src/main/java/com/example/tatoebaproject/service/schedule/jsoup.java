package com.example.tatoebaproject.service.schedule;

import com.example.tatoebaproject.telegram.update.TatoebaObject;
import com.example.tatoebaproject.telegram.update.TelegramUpdateDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;


public class jsoup {
    public static void main(String[] args) throws IOException {
        TelegramUpdateDTO telegramUpdateDTO = new TelegramUpdateDTO();
        String text1 = telegramUpdateDTO.getMessageDTO().getText();


        String word = text1;

        String url = "https://tatoeba.org/en/sentences/search?" + "from=" + "eng" + "&query=" + word + "&to" + "tur";
        Document document = Jsoup.connect(url).get();
        Elements div = document.select("div");
        String attr = div.attr("ng-init");
        System.out.println(attr);
        String[] string = attr.split("vm.init\\(\\[],");
        String[] split = string[1].split(", \\[\\{");
        String result = split[0];
        System.out.println(result);

        TatoebaObject tatoebaObject = new ObjectMapper().readValue(result, TatoebaObject.class);
        String text = tatoebaObject.getText();
        System.out.println(text);

        String text2 = tatoebaObject.getTranslations().get(0).get(0).text;
        System.out.println(text2);


    }


}
