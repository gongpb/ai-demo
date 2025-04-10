package com.gongpb.demo.langchain.demo;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.openai.OpenAiChatModel;

import java.time.Duration;

public class Demo {
    public static void main(String[] args) {
        OpenAiChatModel chatGlmChatModel = OpenAiChatModel.builder()
                // 模型地址
                .baseUrl("https://api.deepseek.com/v1")
                // 模型key
                .apiKey("sk-eaf66d0e33164c6a8a232062e4a8be0a")
                // 最大令牌数
                .maxTokens(1000)
                // 精确度
                .temperature(0d)
                // 超时时间
                .timeout(Duration.ofSeconds(3))
                // 模型名称
                .modelName("deepseek-chat")
                // 重试次数
                .maxRetries(3)
                .build();

        ChatLanguageModel model = OpenAiChatModel.builder()
                .apiKey("demo")
                .build();
        UserMessage userMessage = new UserMessage("你好");
        ChatResponse result = model.chat(userMessage);
        System.out.println(result.aiMessage());
    }
}