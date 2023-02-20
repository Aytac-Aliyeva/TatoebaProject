package com.example.tatoebaproject.service;

import com.example.tatoebaproject.telegram.update.TatoebaObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
public class JsoupGenerator {
    public TatoebaObject jsoup(String word, String from, String to) throws IOException {
        String text;
        String url = "https://tatoeba.org/en/sentences/search?from=" + from + "&query=" + word + "&to=" + to;
        Document document = Jsoup.connect(url).get();
        Elements div = document.select("div");
        List<String> strings = div.eachAttr("ng-init");
        int limit = strings.size();
        int random = (int) (Math.random() * limit);
        String attr = strings.get(random);
        String[] string = attr.split("vm.init\\(\\[], ");
        String[] split = string[1].split(", \\[\\{");
        String result = split[0];
//        System.out.println(result);

        return new ObjectMapper().readValue(result, TatoebaObject.class);

    }

}
