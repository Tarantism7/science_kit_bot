package com.science_kit.science_kit_bot.common.utils;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.regex.Pattern;

public final class CommandUtils {

    private CommandUtils() {
    }

    public static boolean canHandleCommand(Update update, String command) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText().trim()
                .matches("^" + Pattern.quote(command) + "(\\s+.*)?$");
    }

    public static String extractArgument(String text) {
        if (text == null || text.isBlank()) {
            return null;
        }

        String[] parts = text.trim().split("\\s+", 2);

        if (parts.length < 2 || parts[1].isBlank()) {
            return null;
        }

        return parts[1].trim();
    }
}