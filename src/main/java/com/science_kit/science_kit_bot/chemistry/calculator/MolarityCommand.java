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
public class MolarityCommand implements Command {

    private final MolarityCalculator calculator;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    public boolean canHandle(Update update) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText()
                .trim().matches("^/molarity(@\\w+)?(\\s+.*)?$");
    }

    @Override
    public void handle(Update update) {

        Long chatId = update.getMessage().getChatId();

        String[] parts = update.getMessage().getText()
                .trim().split("\\s+");

        if (parts.length != 3) {
            send(chatId, "Usage: /molarity 2 0.5\n"
                    + "Example: 2 moles in 0.5 liters.");
            return;
        }

        try {
            double moles = Double.parseDouble(parts[1]);
            double volume = Double.parseDouble(parts[2]);

            double concentration = calculator.calculate(moles, volume);

            send(chatId, """
                    🧪 Molarity Calculation

                    Amount: %s mol
                    Volume: %s L
                    Concentration: %.4f mol/L
                    """.formatted(moles, volume, concentration));

        } catch (IllegalArgumentException e) {
            send(chatId, "Invalid input: " + e.getMessage());
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
        return "MOLARITY_COMMAND";
    }
}