package com.science_kit.science_kit_bot.bot.commands;

import com.science_kit.science_kit_bot.bot.events.MessageEvent;
import com.science_kit.science_kit_bot.common.service.KeyboardService;
import com.science_kit.science_kit_bot.common.commands.Command;
import com.science_kit.science_kit_bot.common.commands.CommandName;
import com.science_kit.science_kit_bot.common.service.LocalizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class StartCommand implements Command {
    private final ApplicationEventPublisher eventPublisher;
    private final LocalizationService localizationService;
    private final KeyboardService keyboardService;

    @Override
    public boolean canHandle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return false;
        }
        Long chatId = update.getMessage().getChatId();
        String localizedMessage = localizationService.getLocalizedMessage(chatId, "menu.start");
        return update.getMessage().getText().equals(localizedMessage);
    }

    @Override
    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();

            String localizedMessage = localizationService.getLocalizedMessage(chatId, "menu.welcome");

            SendMessage message = SendMessage
                    .builder()
                    .chatId(chatId)
                    .text(localizedMessage)
                    .replyMarkup(keyboardService.mainMenu(chatId))
                    .build();

            eventPublisher.publishEvent(new MessageEvent(this, message));
        }
    }

    @Override
    public String getCommand() {
        return CommandName.START.getCommandName();
    }
}