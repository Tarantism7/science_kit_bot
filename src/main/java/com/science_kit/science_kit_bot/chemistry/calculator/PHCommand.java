package com.science_kit.science_kit_bot.chemistry.calculator;

import com.science_kit.science_kit_bot.common.commands.Command;
import com.science_kit.science_kit_bot.bot.events.MessageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class PHCommand implements Command {

    private final PHCalculator calculator;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean canHandle(Update update) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText()
                .trim().matches("^/ph(@\\w+)?(\\s+.*)?$");
    }

    @Override
    public void handle(Update update) {

        Long chatId = update.getMessage().getChatId();

        String[] parts = update.getMessage().getText()
                .trim().split("\\s+", 2);

        if (parts.length < 2) {
            send(chatId, "Usage: /ph 0.001");
            return;
        }

        try {
            double concentration = Double.parseDouble(parts[1]);

            double ph = calculator.calculate(concentration);

            send(chatId, """
                    🧪 pH Calculation

                    Hydrogen concentration: %s mol/L
                    pH: %.4f
                    """.formatted(concentration, ph));

        } catch (IllegalArgumentException e) {
            send(chatId, "Invalid concentration: " + e.getMessage());
        }
    }

    private void send(Long chatId, String text) {
        eventPublisher.publishEvent(
                new MessageEvent(this,
                        SendMessage.builder()
                                .chatId(chatId)
                                .text(text)
                                .build())
        );
    }

    @Override
    public String getCommand() {
        return "PH_COMMAND";
    }
}