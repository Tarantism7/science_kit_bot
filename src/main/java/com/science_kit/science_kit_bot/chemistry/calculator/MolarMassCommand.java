package com.science_kit.science_kit_bot.chemistry.calculator;

import com.science_kit.science_kit_bot.common.commands.Command;
import com.science_kit.science_kit_bot.common.commands.CommandName;
import com.science_kit.science_kit_bot.common.service.TelegramMessageService;
import com.science_kit.science_kit_bot.element.Element;
import com.science_kit.science_kit_bot.element.ElementService;
import com.science_kit.science_kit_bot.rust.RustChemistryClient;
import com.science_kit.science_kit_bot.rust.RustClientDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MolarMassCommand implements Command {

    private final ChemicalFormulaParser parser;
    private final ElementService elementService;
    private final RustChemistryClient rustChemistryClient;
    private final MolarMassCalculator localCalculator;
    private final TelegramMessageService telegramMessageService;

    @Override
    public boolean canHandle(Update update) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText()
                .trim().matches("^/molar(@\\w+)?(\\s+.*)?$");
    }

    @Override
    public void handle(Update update) {
        Long chatId = update.getMessage().getChatId();

        String[] parts = update.getMessage().getText()
                .trim().split("\\s+", 2);

        if (parts.length < 2 || parts[1].isBlank()) {
            telegramMessageService.send(
                    chatId,
                    "Usage: `/molar H2O`",
                    "Markdown"
            );
            return;
        }

        String formula = parts[1].trim();

        try {
            // 1. Parse formula into symbol counts
            Map<String, Integer> atoms = parser.parse(formula);

            // 2. Fetch atomic masses from PostgreSQL
            var elements = elementService.findBySymbolIn(atoms.keySet());

            Map<String, Double> atomicMasses = elements.stream()
                    .filter(e -> e.getAtomicMass() != null)
                    .collect(Collectors.toMap(
                            Element::getSymbol,
                            e -> e.getAtomicMass().doubleValue()
                    ));

            // 3. Calculate using Rust microservice
            RustClientDto.MolarMassResponse result =
                    rustChemistryClient.calculateMolarMass(formula, atomicMasses);

            // 4. Format breakdown
            StringBuilder breakdownText = new StringBuilder();

            if (result.breakdown() != null) {
                for (var b : result.breakdown()) {
                    breakdownText.append(String.format(
                            "• %s: %d × %.3f = %.3f\n",
                            b.symbol(),
                            b.count(),
                            b.atomicMass(),
                            b.contribution()
                    ));
                }
            }

            String response = String.format(
                    "🧪 *Rust Chemistry Engine*\n\n" +
                            "Formula: `%s`\n" +
                            "Total Molar Mass: `%.4f %s`\n\n" +
                            "Breakdown:\n%s",
                    result.formula(),
                    result.molarMass(),
                    result.unit(),
                    breakdownText
            );

            telegramMessageService.send(chatId, response, "Markdown");

        } catch (Exception rustException) {

            // 5. Fallback to local Java calculator
            try {
                BigDecimal localMass = localCalculator.calculate(formula);

                String fallbackResponse = String.format(
                        "⚠️ *Rust Engine Offline - Local Java Fallback*\n\n" +
                                "Formula: `%s`\n" +
                                "Molar Mass: `%s g/mol`",
                        formula,
                        localMass.toPlainString()
                );

                telegramMessageService.send(
                        chatId,
                        fallbackResponse,
                        "Markdown"
                );

            } catch (Exception localException) {
                telegramMessageService.send(
                        chatId,
                        "Could not calculate molar mass. Please check your formula."
                );
            }
        }
    }

    @Override
    public String getCommand() {
        return CommandName.MOLAR_MASS.getCommandName();
    }
}