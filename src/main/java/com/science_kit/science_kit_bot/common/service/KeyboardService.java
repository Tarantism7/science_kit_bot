package com.science_kit.science_kit_bot.common.service;

import com.science_kit.science_kit_bot.converter.Unit;
import com.science_kit.science_kit_bot.converter.UnitType;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeyboardService {

    private static final int BUTTONS_PER_ROW = 2;

    public ReplyKeyboard mainMenu(Long chatId) {
        List<KeyboardRow> keyboard = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("🔄 Unit Converter"));
        keyboard.add(row1);

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton("🌐 Language"));
        keyboard.add(row2);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        return keyboardMarkup;
    }

    public ReplyKeyboard backToMainMenu(Long chatId) {
        return mainMenu(chatId);
    }

    public ReplyKeyboard buildCategoryKeyboard() {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow currentRow = new KeyboardRow();

        for (UnitType type : UnitType.values()) {
            currentRow.add(new KeyboardButton(formatName(type.name())));

            if (currentRow.size() == BUTTONS_PER_ROW) {
                keyboard.add(currentRow);
                currentRow = new KeyboardRow();
            }
        }
        if (!currentRow.isEmpty()) {
            keyboard.add(currentRow);
        }

        KeyboardRow cancelRow = new KeyboardRow();
        cancelRow.add(new KeyboardButton("❌ Main Menu"));
        keyboard.add(cancelRow);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        return keyboardMarkup;
    }

    public ReplyKeyboard buildUnitKeyboard(UnitType category) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow currentRow = new KeyboardRow();

        for (Unit unit : Unit.values()) {
            if (unit.getType() == category) {
                currentRow.add(new KeyboardButton(formatName(unit.name())));

                if (currentRow.size() == BUTTONS_PER_ROW) {
                    keyboard.add(currentRow);
                    currentRow = new KeyboardRow();
                }
            }
        }
        if (!currentRow.isEmpty()) {
            keyboard.add(currentRow);
        }

        KeyboardRow cancelRow = new KeyboardRow();
        cancelRow.add(new KeyboardButton("❌ Main Menu"));
        keyboard.add(cancelRow);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboard);
        keyboardMarkup.setResizeKeyboard(true);
        keyboardMarkup.setOneTimeKeyboard(false);

        return keyboardMarkup;
    }

    private String formatName(String raw) {
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