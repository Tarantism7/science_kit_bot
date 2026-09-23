package com.science_kit.science_kit_bot.converter;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ConversionKeyboardBuilder {

    private static final int BUTTONS_PER_ROW = 2;

    public static InlineKeyboardMarkup buildCategoryKeyboard() {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        List<InlineKeyboardButton> currentRow = new ArrayList<>();

        for (UnitType type : UnitType.values()) {
            InlineKeyboardButton button = InlineKeyboardButton.builder()
                    .text(formatName(type.name()))
                    .callbackData("conv_cat_" + type.name())
                    .build();

            currentRow.add(button);

            if (currentRow.size() == BUTTONS_PER_ROW) {
                rows.add(new InlineKeyboardRow(currentRow));
                currentRow = new ArrayList<>();
            }
        }

        if (!currentRow.isEmpty()) {
            rows.add(new InlineKeyboardRow(currentRow));
        }

        InlineKeyboardButton resetBtn = InlineKeyboardButton.builder()
                .text("❌ Cancel")
                .callbackData("conv_reset")
                .build();
        rows.add(new InlineKeyboardRow(resetBtn));

        return new InlineKeyboardMarkup(rows);
    }

    public static InlineKeyboardMarkup buildUnitKeyboard(UnitType category, String prefix, String backCallbackData) {
        List<InlineKeyboardRow> rows = new ArrayList<>();
        List<InlineKeyboardButton> currentRow = new ArrayList<>();

        for (Unit unit : Unit.values()) {
            if (unit.getType() == category) {
                InlineKeyboardButton button = InlineKeyboardButton.builder()
                        .text(formatName(unit.name()))
                        .callbackData(prefix + unit.name())
                        .build();

                currentRow.add(button);

                if (currentRow.size() == BUTTONS_PER_ROW) {
                    rows.add(new InlineKeyboardRow(currentRow));
                    currentRow = new ArrayList<>();
                }
            }
        }

        if (!currentRow.isEmpty()) {
            rows.add(new InlineKeyboardRow(currentRow));
        }

        List<InlineKeyboardButton> controlRow = new ArrayList<>();
        controlRow.add(InlineKeyboardButton.builder()
                .text("⬅️ Back")
                .callbackData(backCallbackData)
                .build());
        controlRow.add(InlineKeyboardButton.builder()
                .text("❌ Cancel")
                .callbackData("conv_reset")
                .build());

        rows.add(new InlineKeyboardRow(controlRow));

        return new InlineKeyboardMarkup(rows);
    }

    private static String formatName(String raw) {
        String[] words = raw.split("_");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (!w.isEmpty()) {
                sb.append(Character.toUpperCase(w.charAt(0)))
                        .append(w.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }
}