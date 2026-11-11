package com.science_kit.science_kit_bot.chemistry.calculator;

import com.science_kit.science_kit_bot.bot.events.MessageEvent;
import com.science_kit.science_kit_bot.common.commands.AbstractCommand;
import com.science_kit.science_kit_bot.common.commands.CommandName;
import com.science_kit.science_kit_bot.common.utils.CommandUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Slf4j
public class MolarityCommand extends AbstractCommand {

    private final MolarityCalculator calculator;
    private final ApplicationEventPublisher eventPublisher;

    public MolarityCommand(
            MolarityCalculator calculator,
            ApplicationEventPublisher eventPublisher
    ) {
        super(CommandName.MOLARITY);
        this.calculator = calculator;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void handle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        Long chatId = update.getMessage().getChatId();

        String arguments = CommandUtils.extractArgument(
                update.getMessage().getText()
        );

        if (arguments == null) {
            send(chatId, "Usage: /molarity 2 0.5\n"
                    + "Example: 2 moles in 0.5 liters.");
            return;
        }

        String[] parts = arguments.split("\\s+");

        if (parts.length != 2) {
            send(chatId, "Usage: /molarity 2 0.5\n"
                    + "Example: 2 moles in 0.5 liters.");
            return;
        }

        try {
            double moles = Double.parseDouble(parts[0]);
            double volume = Double.parseDouble(parts[1]);

            double concentration = calculator.calculate(moles, volume);

            send(chatId, """
                    🧪 Molarity Calculation

                    Amount: %s mol
                    Volume: %s L
                    Concentration: %.4f mol/L
                    """.formatted(moles, volume, concentration));

        } catch (IllegalArgumentException e) {
            log.warn("Invalid molarity input: {}", e.getMessage());
            send(chatId, "Invalid input: " + e.getMessage());
        }
    }

    private void send(Long chatId, String text) {
        eventPublisher.publishEvent(
                new MessageEvent(
                        this,
                        SendMessage.builder()
                                .chatId(chatId)
                                .text(text)
                                .build()
                )
        );
    }
}