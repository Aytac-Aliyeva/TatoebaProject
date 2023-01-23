package com.example.tatoebaproject.telegram.send.text;

import com.example.tatoebaproject.telegram.send.ReplyKeyboard;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
//import com.azal.bot.dto.telegram.send.ReplyKeyboard;

@Data
@Builder
//@RequiredArgsConstructor
public class SendMessageDTO {

    @JsonProperty("chat_id")
    private Long chatId;

    @JsonProperty("text")
    private String text;


}
