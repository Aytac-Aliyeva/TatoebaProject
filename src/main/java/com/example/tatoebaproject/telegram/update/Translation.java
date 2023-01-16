package com.example.tatoebaproject.telegram.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Translation {
    public int id;
    public String text;
    public String lang;
    public int correctness;
    public Object script;
    public ArrayList<Object> transcriptions;
    public ArrayList<Object> audios;
    public boolean isDirect;
    public String lang_name;
    public String dir;
    public String lang_tag;
}


