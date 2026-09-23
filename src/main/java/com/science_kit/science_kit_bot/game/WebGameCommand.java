package com.science_kit.science_kit_bot.game;

import com.science_kit.science_kit_bot.common.commands.Command;
import com.science_kit.science_kit_bot.bot.events.MessageEvent;
import com.science_kit.science_kit_bot.converter.UnitConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;

import java.util.List;

/*
TODO: update command and add it to enum
 */

@Component
@RequiredArgsConstructor
public class WebGameCommand implements Command {

    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean canHandle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return false;
        }
        String text = update.getMessage().getText().trim();
        return text.equalsIgnoreCase("/webgame") || text.equalsIgnoreCase("🎮 Play HTML Game");
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();

        WebAppInfo webAppInfo = new WebAppInfo("https://your-public-domain.ngrok-free.app/games/elementQuizGame/index.html");

        InlineKeyboardButton playButton = InlineKeyboardButton.builder()
                .text("🚀 Open Element Game")
                .webApp(webAppInfo)
                .build();

        InlineKeyboardMarkup keyboard = new InlineKeyboardMarkup(List.of(new InlineKeyboardRow(playButton)));

        String text = "🎮 *HTML5 Mini-Game Ready!*\n\nTap the button below to launch the visual app inside Telegram:";

        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(UnitConverter.escapeMarkdownV2(text))
                .parseMode("MarkdownV2")
                .replyMarkup(keyboard)
                .build();

        eventPublisher.publishEvent(new MessageEvent(this, message));
    }

    @Override
    public String getCommand() {
        return "/webgame";
    }
}