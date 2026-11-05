package com.science_kit.science_kit_bot.element;

import com.science_kit.science_kit_bot.common.commands.Command;
import com.science_kit.science_kit_bot.common.commands.CommandName;
import com.science_kit.science_kit_bot.common.service.TelegramMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
@Slf4j
public class ElementCommand implements Command {

    private final TelegramMessageService telegramMessageService;
    private final ElementService elementService;

    @Override
    public boolean canHandle(Update update) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText().startsWith("/element");
    }

    @Override
    public void handle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        Long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();
        String argument = extractArgument(text);

        log.info("User requested element '{}'", argument);

        if (argument == null || argument.isBlank()) {
            log.warn("Invalid /element request: missing argument");

            telegramMessageService.send(chatId, "Please enter a valid element, e.g. /element 3");
            return;
        }

        elementService.find(argument).ifPresentOrElse(
                        element -> telegramMessageService.send(
                                chatId,
                                formatElement(element)), () -> telegramMessageService.send(chatId,
                                "Element not found: " + argument));
    }

    private String extractArgument(String text) {
        String[] parts = text.trim().split("\\s+", 2);

        if (parts.length < 2) {
            return null;
        }

        return parts[1];
    }

    private String formatElement(Element e) {
        return """
                %s (%s)
                
                Atomic number: %d
                Atomic mass: %s
                Group: %d
                Period: %d
                Category: %s
                Electronegativity: %s
                Melting point: %s K
                Electron configuration: %s
                """.formatted(
                e.getName(),
                e.getSymbol(),
                e.getAtomicNumber(),
                e.getAtomicMass(),
                e.getGroupNumber(),
                e.getPeriod(),
                e.getCategory(),
                e.getElectronegativity(),
                e.getMeltingPointK(),
                e.getElectronConfiguration()
        );
    }

    @Override
    public String getCommand() {
        return CommandName.ELEMENT.getCommandName();
    }
}