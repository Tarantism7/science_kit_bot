package com.science_kit.science_kit_bot.bot.commands;

import com.science_kit.science_kit_bot.bot.events.MessageEvent;
import com.science_kit.science_kit_bot.bot.model.Element;
import com.science_kit.science_kit_bot.bot.service.ElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class ElementCommand implements Command {

    private final ApplicationEventPublisher eventPublisher;
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

        SendMessage message;

        if (argument == null || argument.isBlank()) {
            message = SendMessage.builder()
                    .chatId(chatId)
                    .text("Please enter a valid element, e.g. /element 3")
                    .build();
        } else {
            message = elementService.find(argument)
                    .map(element -> SendMessage.builder()
                            .chatId(chatId)
                            .text(formatElement(element))
                            .build())
                    .orElseGet(() -> null);
        }

        eventPublisher.publishEvent(new MessageEvent(this, message));
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
