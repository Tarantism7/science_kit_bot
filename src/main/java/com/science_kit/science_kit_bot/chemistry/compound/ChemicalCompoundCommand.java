package com.science_kit.science_kit_bot.chemistry.compound;

import com.science_kit.science_kit_bot.common.commands.AbstractCommand;
import com.science_kit.science_kit_bot.common.commands.CommandName;
import com.science_kit.science_kit_bot.common.service.TelegramMessageService;
import com.science_kit.science_kit_bot.common.utils.CommandUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Slf4j
public class ChemicalCompoundCommand extends AbstractCommand {

    private final ChemicalCompoundService chemicalCompoundService;
    private final TelegramMessageService telegramMessageService;

    public ChemicalCompoundCommand(
            ChemicalCompoundService chemicalCompoundService,
            TelegramMessageService telegramMessageService
    ) {
        super(CommandName.CHEM_COMPOUND);
        this.chemicalCompoundService = chemicalCompoundService;
        this.telegramMessageService = telegramMessageService;
    }

    @Override
    public void handle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        Long chatId = update.getMessage().getChatId();

        String formula = CommandUtils.extractArgument(
                update.getMessage().getText()
        );

        if (formula == null) {
            log.warn("Invalid /cc request: missing compound formula");

            telegramMessageService.send(
                    chatId,
                    "Chemical compound\nUsage: /cc H2O"
            );
            return;
        }

        log.info("User requested chemical compound '{}'", formula);

        var compound = chemicalCompoundService.find(formula);

        if (compound.isEmpty()) {
            telegramMessageService.send(
                    chatId,
                    "Compound not found in the database: " + formula
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
}