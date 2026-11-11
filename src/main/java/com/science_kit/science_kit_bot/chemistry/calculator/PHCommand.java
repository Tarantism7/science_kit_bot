package com.science_kit.science_kit_bot.chemistry.calculator;

import com.science_kit.science_kit_bot.bot.events.MessageEvent;
import com.science_kit.science_kit_bot.common.commands.AbstractCommand;
import com.science_kit.science_kit_bot.common.commands.CommandName;
import com.science_kit.science_kit_bot.common.utils.CommandUtils;
import com.science_kit.science_kit_bot.exception.PHCalculationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Slf4j
public class PHCommand extends AbstractCommand {

    private final PHCalculator calculator;
    private final ApplicationEventPublisher eventPublisher;

    public PHCommand(
            PHCalculator calculator,
            ApplicationEventPublisher eventPublisher
    ) {
        super(CommandName.PH);
        this.calculator = calculator;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void handle(Update update) {

        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        Long chatId = update.getMessage().getChatId();

        String argument = CommandUtils.extractArgument(
                update.getMessage().getText()
        );

        if (argument == null) {
            send(chatId, """
                    🧪 pH Calculator

                    Usage: /ph 0.001

                    Example: /ph 0.001
                    """);
            return;
        }

        try {
            double concentration = Double.parseDouble(argument);

            double ph = calculator.calculate(concentration);

            log.info("pH calculated successfully for concentration: {}",
                    concentration);

            send(chatId, """
                    🧪 pH Calculation

                    Hydrogen concentration: %s mol/L
                    pH: %.4f
                    """.formatted(concentration, ph));

        } catch (NumberFormatException e) {

            log.warn("Invalid pH concentration input: {}", argument);

            send(chatId, "Invalid input. Please enter a valid number.");

        } catch (PHCalculationException e) {

            log.warn("pH calculation failed: {}", e.getMessage());

            send(chatId, "Invalid concentration: " + e.getMessage());
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