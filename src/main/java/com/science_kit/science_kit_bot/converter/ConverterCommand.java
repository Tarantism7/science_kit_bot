package com.science_kit.science_kit_bot.converter;

import com.science_kit.science_kit_bot.common.commands.Command;
import com.science_kit.science_kit_bot.common.commands.CommandName;
import com.science_kit.science_kit_bot.bot.events.MessageEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Command service managing /convert and /convert help [category] operations.
 */
@Service
@RequiredArgsConstructor
public class ConverterCommand implements Command {

    private final ApplicationEventPublisher eventPublisher;

    /**
     * Regex matching standard numeric conversion inputs:
     * - Group 1: Numerical value (e.g., -10, 5.5, 1e3)
     * - Group 2: Source unit/alias (e.g., km, kg, c)
     * - Group 3: Target unit/alias (e.g., mi, lbs, f)
     */
    private static final Pattern CONVERT_PATTERN = Pattern.compile(
            "(?i)^/convert\\s+(-?\\d+(?:\\.\\d+)?(?:e[-+]?\\d+)?)\\s+(\\S+)(?:\\s+to)?\\s+(\\S+)$"
    );

    /**
     * Regex matching help subcommands:
     * - Matches '/convert help' (Global directory)
     * - Matches '/convert help mass', '/convert help speed' (Filtered directory via Group 1)
     */
    private static final Pattern HELP_PATTERN = Pattern.compile(
            "(?i)^/convert\\s+help(?:\\s+(.+))?$"
    );

    @Override
    public boolean canHandle(Update update) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText().trim().toLowerCase().startsWith("/convert");
    }

    @Override
    public void handle(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        Long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText().trim();

        if (text.equalsIgnoreCase("/convert")) {
            sendHelp(chatId, null);
            return;
        }

        Matcher helpMatcher = HELP_PATTERN.matcher(text);
        if (helpMatcher.matches()) {
            String category = helpMatcher.group(1);
            sendHelp(chatId, category);
            return;
        }

        Matcher matcher = CONVERT_PATTERN.matcher(text);
        SendMessage message;

        if (!matcher.matches()) {
            message = SendMessage.builder()
                    .chatId(chatId)
                    .text("""
                            ⚠️ *Invalid format\\!* Usage examples:
                            • `/convert 10 km to miles`
                            • `/convert 100 c f`
                            • `/convert 5.5 kg lbs`
                            
                            Type `/convert help` or `/convert help mass` for unit lists\\.
                            """)
                    .parseMode("MarkdownV2")
                    .build();
        } else {
            double amount = Double.parseDouble(matcher.group(1));
            String fromStr = matcher.group(2);
            String toStr = matcher.group(3);

            message = processConversion(chatId, amount, fromStr, toStr);
        }

        eventPublisher.publishEvent(new MessageEvent(this, message));
    }

    /**
     * Helper to dispatch filtered or global help payloads.
     */
    private void sendHelp(Long chatId, String category) {
        SendMessage helpMsg = SendMessage.builder()
                .chatId(chatId)
                .text(UnitConverter.generateHelpMessage(category))
                .parseMode("MarkdownV2")
                .build();
        eventPublisher.publishEvent(new MessageEvent(this, helpMsg));
    }

    private SendMessage processConversion(Long chatId, double amount, String fromStr, String toStr) {
        Unit fromUnit = Unit.parse(fromStr);
        Unit toUnit = Unit.parse(toStr);

        if (fromUnit == null || toUnit == null) {
            String unknown = (fromUnit == null) ? fromStr : toStr;
            String escapedUnknown = UnitConverter.escapeMarkdownV2(unknown);
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(String.format("❌ Unknown unit: *'%s'*\\. Use `/convert help` to list available units\\.", escapedUnknown))
                    .parseMode("MarkdownV2")
                    .build();
        }

        try {
            double result = UnitConverter.convert(amount, fromUnit, toUnit);

            String resultFormatted = (Math.abs(result) < 1e-3 || Math.abs(result) > 1e6)
                    ? String.format("%.4e", result)
                    : String.format("%.4f", result).replaceAll("0+$", "").replaceAll("\\.$", "");

            String text = String.format("⚖️ *%s %s* \\= *%s %s*",
                    UnitConverter.escapeMarkdownV2(String.format("%.4g", amount)),
                    UnitConverter.escapeMarkdownV2(fromUnit.name().toLowerCase()),
                    UnitConverter.escapeMarkdownV2(resultFormatted),
                    UnitConverter.escapeMarkdownV2(toUnit.name().toLowerCase()));

            return SendMessage.builder()
                    .chatId(chatId)
                    .text(text)
                    .parseMode("MarkdownV2")
                    .build();
        } catch (IllegalArgumentException e) {
            return SendMessage.builder()
                    .chatId(chatId)
                    .text(String.format("⚠️ Cannot convert between *%s* \\(%s\\) and *%s* \\(%s\\)\\.",
                            UnitConverter.escapeMarkdownV2(fromUnit.name().toLowerCase()),
                            UnitConverter.escapeMarkdownV2(fromUnit.getType().name()),
                            UnitConverter.escapeMarkdownV2(toUnit.name().toLowerCase()),
                            UnitConverter.escapeMarkdownV2(toUnit.getType().name())))
                    .parseMode("MarkdownV2")
                    .build();
        }
    }

    @Override
    public String getCommand() {
        return CommandName.CONVERT.getCommandName();
    }
}