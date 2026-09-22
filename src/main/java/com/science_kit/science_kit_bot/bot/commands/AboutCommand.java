package com.science_kit.science_kit_bot.bot.commands;

import com.science_kit.science_kit_bot.bot.events.MessageEvent;
import com.science_kit.science_kit_bot.bot.utils.UnitConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@RequiredArgsConstructor
public class AboutCommand implements Command {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean canHandle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return false;
        }
        String text = update.getMessage().getText().trim();
        return text.equalsIgnoreCase("/about") || text.equalsIgnoreCase("ℹ️ About");
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();

        String aboutText = "🤖 *Science Kit Bot" +
                "*\n\n" +
                "An interactive multi-purpose utility bot designed to help you perform accurate physical unit conversions, track metrics, and solve technical queries seamlessly\\.\n\n" +
                "• *Version:* `1.0.0`\n" +
                "• *Framework:* Spring Boot\n" +
                "• *Features:* Interactive wizards, multi-unit support, and custom keyboards\\.";

        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(UnitConverter.escapeMarkdownV2(aboutText))
                .parseMode("MarkdownV2")
                .build();

        eventPublisher.publishEvent(new MessageEvent(this, message));
    }

    @Override
    public String getCommand() {
        return CommandName.ABOUT.getCommandName();
    }
}