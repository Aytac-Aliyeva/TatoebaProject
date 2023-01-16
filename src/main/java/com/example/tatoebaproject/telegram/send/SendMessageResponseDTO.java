package com.example.tatoebaproject.telegram.send;

//import com.azal.bot.dto.telegram.update.ParameterDTO;
import com.example.tatoebaproject.telegram.update.ParameterDTO;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendMessageResponseDTO {

    @JsonProperty("ok")
    private Boolean ok;

    @JsonProperty("error_code")
    private Long errorCode;

    @JsonProperty("description")
    private String description;

    @JsonProperty("parameters")
    private ParameterDTO parameters;


}
