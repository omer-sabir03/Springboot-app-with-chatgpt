package com.chatgpt.in.springai.response;

import com.chatgpt.in.springai.request.Message;
import lombok.Data;

import java.util.List;

@Data
public class BotResponse {

    private List<Choice> choices;
    public static class Choice {

        private int index;
        private Message message;

    }
}