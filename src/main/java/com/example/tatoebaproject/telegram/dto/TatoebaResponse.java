package com.example.tatoebaproject.telegram.dto;

import lombok.Builder;


@Builder
public class TatoebaResponse {
    String fromLanguage;
    String toLanguage;

    @Override
    public String toString() {
        return fromLanguage + '\n' +
                toLanguage;
    }
}
