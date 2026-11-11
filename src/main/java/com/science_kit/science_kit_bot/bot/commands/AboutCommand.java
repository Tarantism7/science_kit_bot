package com.science_kit.science_kit_bot.bot.commands;

import com.science_kit.science_kit_bot.bot.events.MessageEvent;
import com.science_kit.science_kit_bot.common.commands.AbstractCommand;
import com.science_kit.science_kit_bot.common.commands.CommandName;
import com.science_kit.science_kit_bot.converter.UnitConverter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class AboutCommand extends AbstractCommand {

    private final ApplicationEventPublisher eventPublisher;

    public AboutCommand(ApplicationEventPublisher eventPublisher) {
        super(CommandName.ABOUT);
        this.eventPublisher = eventPublisher;
    }

    @Override
    public boolean canHandle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return false;
        }

        String text = update.getMessage().getText().trim();

        return super.canHandle(update)
                || text.equalsIgnoreCase("ℹ️ About");
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();

        String aboutText = "🤖 *Science Kit Bot" +
                "*\n\n" +
                "An interactive multi-purpose utility bot designed to help you perform accurate physical unit conversions, track metrics, and solve technical queries seamlessly\\.\n\n" +
                "• *Version:* `0.2.0`\n" +
                "• *Framework:* Spring Boot\n" +
                "• *Features:* Interactive wizards, multi-unit support, and custom keyboards\\.";

        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(UnitConverter.escapeMarkdownV2(aboutText))
                .parseMode("MarkdownV2")
                .build();

        eventPublisher.publishEvent(new MessageEvent(this, message));
    }
}