package com.science_kit.science_kit_bot.common.service;

import com.science_kit.science_kit_bot.bot.events.MessageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Service
@RequiredArgsConstructor
public class TelegramMessageService {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void send(Long chatId, String text) {
        send(chatId, text, null);
    }

    public void send(Long chatId, String text, String parseMode) {
        var builder = SendMessage.builder()
                .chatId(chatId)
                .text(text);

        if (parseMode != null) {
            builder.parseMode(parseMode);
        }

        applicationEventPublisher.publishEvent(
                new MessageEvent(this, builder.build())
        );
    }
}
