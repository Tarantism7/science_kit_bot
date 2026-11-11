
package com.science_kit.science_kit_bot.converter;

import com.science_kit.science_kit_bot.common.commands.AbstractCommand;
import com.science_kit.science_kit_bot.common.commands.CommandName;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class InteractiveConverterCommand extends AbstractCommand {

    private final InteractiveConverterService interactiveConverterService;

    public InteractiveConverterCommand(
            InteractiveConverterService interactiveConverterService
    ) {
        super(CommandName.INTERACTIVE_CONVERTER);
        this.interactiveConverterService = interactiveConverterService;
    }

    @Override
    public boolean canHandle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return false;
        }

        String text = update.getMessage().getText().trim();

        return super.canHandle(update)
                || text.equals("🔄 Unit Converter");
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();
        Long userId = update.getMessage().getFrom().getId();

        interactiveConverterService.startWizard(chatId, userId);
    }
}