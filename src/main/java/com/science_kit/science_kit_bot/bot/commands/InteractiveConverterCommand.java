package com.science_kit.science_kit_bot.bot.commands;

import com.science_kit.science_kit_bot.bot.service.InteractiveConverterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class InteractiveConverterCommand implements Command {

    private final InteractiveConverterService interactiveConverterService;

    @Override
    public boolean canHandle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return false;
        }
        String text = update.getMessage().getText().trim();
        return text.equals("🔄 Unit Converter") || text.equalsIgnoreCase("/wizard");
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        Long userId = update.getMessage().getFrom().getId();
        interactiveConverterService.startWizard(chatId, userId);
    }

    @Override
    public String getCommand() {
        return CommandName.INTERACTIVE_CONVERTER.getCommandName();
    }
}