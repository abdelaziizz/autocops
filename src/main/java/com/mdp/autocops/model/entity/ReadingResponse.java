package com.mdp.autocops.model.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class ReadingResponse {

    private String message;
    private List<Map> maps;


}
