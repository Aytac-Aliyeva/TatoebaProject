package com.example.tatoebaproject.telegram.dto;

import lombok.Builder;
import lombok.Data;


@Builder
public class TelegramResponseType {
    String fromLanguage;
    String toLanguage;

    @Override
    public String toString() {
        return  fromLanguage + '\n' +
                toLanguage;
    }
}
