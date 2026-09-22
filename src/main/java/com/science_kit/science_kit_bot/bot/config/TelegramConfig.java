package com.science_kit.science_kit_bot.bot.config;

import com.science_kit.science_kit_bot.bot.Bot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
public class TelegramConfig {
    @Bean
    public TelegramClient telegramClient(Bot bot) {
        return new OkHttpTelegramClient(bot.getBotToken());
    }
}
