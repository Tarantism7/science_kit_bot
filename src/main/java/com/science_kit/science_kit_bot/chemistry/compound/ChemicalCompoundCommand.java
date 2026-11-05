
package com.science_kit.science_kit_bot.chemistry.compound;

import com.science_kit.science_kit_bot.common.commands.Command;
import com.science_kit.science_kit_bot.common.commands.CommandName;
import com.science_kit.science_kit_bot.common.service.TelegramMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class ChemicalCompoundCommand implements Command {

    private final ChemicalCompoundService chemicalCompoundService;
    private final TelegramMessageService telegramMessageService;

    @Override
    public boolean canHandle(Update update) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText().startsWith("/cc");
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();

        String[] parts = update.getMessage().getText()
                .trim()
                .split("\\s+", 2);

        if (parts.length != 2 || parts[1].isBlank()) {
            telegramMessageService.send(
                    chatId,
                    "Chemical compound\nUsage: /chem_compound H2O"
            );
            return;
        }

        var compound = chemicalCompoundService.find(parts[1]);

        if (compound.isEmpty()) {
            telegramMessageService.send(
                    chatId,
                    "Compound not found in the database."
            );
            return;
        }

        var c = compound.get();

        String response = """
                🧪 *%s*

                *Chemical Formula:* `%s`
                *Molar Mass:* %s g/mol
                *Type:* %s
                *Description:* %s
                *Appearance:* %s
                """.formatted(
                c.getName(),
                c.getFormula(),
                c.getMolarMass(),
                c.getCompoundType(),
                c.getDescription() != null ? c.getDescription() : "Not available",
                c.getAppearance() != null ? c.getAppearance() : "Not specified"
        );

        telegramMessageService.send(chatId, response, "Markdown");
    }

    @Override
    public String getCommand() {
        return CommandName.CHEM_COMPOUND.getCommandName();
    }
}
