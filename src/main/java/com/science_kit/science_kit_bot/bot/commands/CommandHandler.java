package com.science_kit.science_kit_bot.bot.commands;

import com.science_kit.science_kit_bot.bot.events.MessageEvent;
import com.science_kit.science_kit_bot.bot.service.InteractiveConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CommandHandler {

    private final Collection<Command> commands;
    private final InteractiveConverterService interactiveConverterService;
    private final ApplicationEventPublisher eventPublisher;

    public void handle(Update update) {
        if (update.hasCallbackQuery()) {
            String callbackId = update.getCallbackQuery().getId();

            interactiveConverterService.handleCallback(update);

            AnswerCallbackQuery answer = AnswerCallbackQuery.builder()
                    .callbackQueryId(callbackId)
                    .build();
            eventPublisher.publishEvent(new MessageEvent(this, answer));
            return;
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            Long chatId = update.getMessage().getChatId();
            Long userId = update.getMessage().getFrom().getId();
            String text = update.getMessage().getText();

            if (interactiveConverterService.handleTextInput(chatId, userId, text)) {
                return;
            }

            for (Command command : commands) {
                if (command.canHandle(update)) {
                    command.handle(update);
                    return;
                }
            }
        }
    }
}