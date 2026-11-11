package com.science_kit.science_kit_bot.chemistry.formula;

import com.science_kit.science_kit_bot.common.commands.AbstractCommand;
import com.science_kit.science_kit_bot.common.commands.CommandName;
import com.science_kit.science_kit_bot.common.service.TelegramMessageService;
import com.science_kit.science_kit_bot.common.utils.CommandUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Slf4j
public class ChemistryFormulaCommand extends AbstractCommand {

    private final ChemicalFormulaService chemicalFormulaService;
    private final TelegramMessageService telegramMessageService;

    public ChemistryFormulaCommand(
            ChemicalFormulaService chemicalFormulaService,
            TelegramMessageService telegramMessageService
    ) {
        super(CommandName.CHEM_FORMULA);
        this.chemicalFormulaService = chemicalFormulaService;
        this.telegramMessageService = telegramMessageService;
    }

    @Override
    public void handle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        Long chatId = update.getMessage().getChatId();

        String query = CommandUtils.extractArgument(
                update.getMessage().getText()
        );

        if (query == null) {
            telegramMessageService.send(
                    chatId,
                    "Chemistry formula\nUsage: /cf molarity"
            );
            return;
        }

        log.info("User requested chemistry formula '{}'", query);

        var formulas = chemicalFormulaService.search(query);

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
}