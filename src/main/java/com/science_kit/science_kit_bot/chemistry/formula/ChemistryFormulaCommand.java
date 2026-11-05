
package com.science_kit.science_kit_bot.chemistry.formula;

import com.science_kit.science_kit_bot.common.commands.Command;
import com.science_kit.science_kit_bot.common.commands.CommandName;
import com.science_kit.science_kit_bot.common.service.TelegramMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@RequiredArgsConstructor
public class ChemistryFormulaCommand implements Command {

    private final ChemicalFormulaService chemicalFormulaService;
    private final TelegramMessageService telegramMessageService;

    @Override
    public boolean canHandle(Update update) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText().startsWith("/cf");
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
                    "Chemistry formula\nUsage: /cf molarity"
            );
            return;
        }

        var formulas = chemicalFormulaService.search(parts[1]);

        if (formulas.isEmpty()) {
            telegramMessageService.send(
                    chatId,
                    "No chemistry formula found."
            );
            return;
        }

        StringBuilder result = new StringBuilder();

        for (var formula : formulas) {
            result.append("🧪 *")
                    .append(formula.getName())
                    .append("*\n")

                    .append("*Category:* ")
                    .append(formula.getCategory())
                    .append("\n\n")

                    .append("*Formula:* `")
                    .append(formula.getFormula())
                    .append("`\n\n")

                    .append("*Variables:* ")
                    .append(formula.getVariables())
                    .append("\n")

                    .append("*Description:* ")
                    .append(formula.getDescription() != null
                            ? formula.getDescription()
                            : "Not available")
                    .append("\n")

                    .append("*Units:* ")
                    .append(formula.getUnits() != null
                            ? formula.getUnits()
                            : "Not specified")
                    .append("\n\n");
        }

        telegramMessageService.send(
                chatId,
                result.toString(),
                "Markdown"
        );
    }

    @Override
    public String getCommand() {
        return CommandName.CHEM_FORMULA.getCommandName();
    }
}
