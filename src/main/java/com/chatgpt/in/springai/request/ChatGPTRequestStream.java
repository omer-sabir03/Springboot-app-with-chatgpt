package com.chatgpt.in.springai.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatGPTRequestStream {

    private String model;
    private List<Message> messages;

    private boolean stream;

    public ChatGPTRequestStream(String model, String prompt,boolean stream) {
        this.model = model;
        this.messages = new ArrayList<>();
        this.messages.add(new Message("user",prompt));
        this.stream=stream;
    }

}
