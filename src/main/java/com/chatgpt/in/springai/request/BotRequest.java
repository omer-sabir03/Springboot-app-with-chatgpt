package com.chatgpt.in.springai.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BotRequest {

    private String model;
    private List<Message> messages;
    private int n;
    private double temperature;
    private int max_tokens;

}
