package com.chatgpt.in.springai.controller;

import com.chatgpt.in.springai.request.ChatGPTRequest;

import com.chatgpt.in.springai.request.ChatGPTRequestStream;
import com.chatgpt.in.springai.response.ChatCompletionChunk;
import com.chatgpt.in.springai.response.ChatGptResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
public class BotController {
    @Value("${openai.model}")
    private String model;

    @Value(("${openai.api.url}"))
    private String apiURL;

    @Value("${openai.api.key}")
    private String openaiApiKey;


    @Autowired
    private RestTemplate template;

    @GetMapping("/chat")
    public String chat(@RequestParam("prompt") String prompt) {
        ChatGPTRequest request = new ChatGPTRequest(model, prompt);
        ChatGptResponse chatGptResponse = template.postForObject(apiURL, request, ChatGptResponse.class);
        return chatGptResponse.getChoices().get(0).getMessage().getContent();
    }

    //    @GetMapping("/chatstream")
//    public ResponseEntity<Flux<ChatCompletionChunk>> chatstream(@RequestParam("prompt") String prompt){
//        WebClient webClient = WebClient.builder()
//                .baseUrl(apiURL)
//                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openaiApiKey)
//                .build();
//
//        Flux<ChatCompletionChunk> result = webClient.post()
//                .uri("")
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(prompt)
//                .retrieve()
//                .bodyToFlux(new ParameterizedTypeReference<ChatCompletionChunk>() {});
//
//        result.subscribe(
//                chunk -> {
//                    if (chunk.getChoices() != null && !chunk.getChoices().isEmpty() &&
//                            chunk.getChoices().get(0).getDelta() != null &&
//                            chunk.getChoices().get(0).getDelta().getContent() != null) {
//                        System.out.print(chunk.getChoices().get(0).getDelta().getContent());
//                       System.out.print(chunk.getChoices().get(0).getDelta().getContent());
//                    }
//                },
//                error -> System.err.println("Error: " + error.getMessage()),
//                () -> System.out.println("Stream completed"));
//
//        return null;
//    }
//    @GetMapping(value = "/chatstream",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
//    public ResponseEntity<Flux<ChatCompletionChunk>> chatstream(@RequestParam("prompt") String prompt) {
//        ChatGPTRequestStream request = new ChatGPTRequestStream(model, prompt,true);
//        WebClient webClient = WebClient.builder()
//                .baseUrl(apiURL)
//                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openaiApiKey)
//                .build();
//
//        Flux<ChatCompletionChunk> result = webClient.post()
//                .accept(MediaType.TEXT_EVENT_STREAM)
//                .contentType(MediaType.APPLICATION_JSON)
//                .bodyValue(request)
//                .retrieve()
//                .bodyToFlux(new ParameterizedTypeReference<ChatCompletionChunk>() {});
//        return ResponseEntity.ok()
//                .body(result);
//    }
    @GetMapping(value = "/chatstream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> chatstream(@RequestParam("prompt") String prompt) {
        ChatGPTRequestStream request = new ChatGPTRequestStream(model, prompt, true);
        WebClient webClient = WebClient.builder()
                .baseUrl(apiURL)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openaiApiKey)
                .build();

        return webClient.post()
                .accept(MediaType.TEXT_EVENT_STREAM)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToFlux(ChatCompletionChunk.class) // Assuming ChatCompletionChunk has the content field
                .filter(chunk -> chunk.getChoices() != null && !chunk.getChoices().isEmpty() &&
                        chunk.getChoices().get(0).getDelta() != null &&
                        chunk.getChoices().get(0).getDelta().getContent() != null)
                .map(chunk -> chunk.getChoices().get(0).getDelta().getContent());
    }
}