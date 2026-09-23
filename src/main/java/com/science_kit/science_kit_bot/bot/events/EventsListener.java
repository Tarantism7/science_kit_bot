package com.science_kit.science_kit_bot.bot.events;

import com.science_kit.science_kit_bot.common.service.MessageTrackerService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.io.Serializable;

@Component
@RequiredArgsConstructor
public class EventsListener {
    private final TelegramClient telegramClient;
    private final MessageTrackerService messageTrackerService;

    @EventListener
    public void on(MessageEvent event) throws TelegramApiException {
        BotApiMethod<? extends Serializable> message = event.getMessage();

        Object result = telegramClient.execute(message);

        if (result instanceof Message sentMessage && sentMessage.getMessageId() != null) {
            messageTrackerService.saveLastMessage(
                    sentMessage.getChatId(),
                    sentMessage.getMessageId()
            );
        }
    }
}