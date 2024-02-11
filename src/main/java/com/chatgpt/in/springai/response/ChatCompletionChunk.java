package com.chatgpt.in.springai.response;

import lombok.Data;

import java.util.List;

@Data
public class ChatCompletionChunk {
    private String id;
    private String object;
    private  long created;
    private  String model;
    private String system_fingerprint;
    private List<Choice> choices;


}
