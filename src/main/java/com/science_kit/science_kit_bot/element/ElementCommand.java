
package com.science_kit.science_kit_bot.element;

import com.science_kit.science_kit_bot.common.commands.AbstractCommand;
import com.science_kit.science_kit_bot.common.commands.CommandName;
import com.science_kit.science_kit_bot.common.service.TelegramMessageService;
import com.science_kit.science_kit_bot.common.utils.CommandUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Slf4j
public class ElementCommand extends AbstractCommand {

    private final TelegramMessageService telegramMessageService;
    private final ElementService elementService;

    public ElementCommand(
            TelegramMessageService telegramMessageService,
            ElementService elementService
    ) {
        super(CommandName.ELEMENT);
        this.telegramMessageService = telegramMessageService;
        this.elementService = elementService;
    }

    @Override
    public void handle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        Long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();
        String argument = CommandUtils.extractArgument(text);

        if (argument == null || argument.isBlank()) {
            log.warn("Invalid /elem request: missing argument");

            telegramMessageService.send(
                    chatId,
                    "Please enter a valid element, e.g. /elem 3"
            );
            return;
        }

        log.info("User requested element '{}'", argument);

        elementService.find(argument).ifPresentOrElse(
                element -> telegramMessageService.send(
                        chatId,
                        formatElement(element)
                ),
                () -> telegramMessageService.send(
                        chatId,
                        "Element not found: " + argument
                )
        );
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
}
