package com.gongpb.demo.langchain.demo;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

public class Demo {
    public static void main(String[] args) {
        ChatLanguageModel model = OpenAiChatModel.builder().apiKey("demo")
                .modelName("gpt-4o-mini").build();

    }
}