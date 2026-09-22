package com.science_kit.science_kit_bot.bot.service;

import com.science_kit.science_kit_bot.bot.events.MessageEvent;
import com.science_kit.science_kit_bot.bot.model.ConversionSession;
import com.science_kit.science_kit_bot.bot.utils.ConversionKeyboardBuilder;
import com.science_kit.science_kit_bot.bot.utils.UnitConverter;
import com.science_kit.science_kit_bot.bot.utils.UnitConverter.Unit;
import com.science_kit.science_kit_bot.bot.utils.UnitConverter.UnitType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class InteractiveConverterService {

    private final ApplicationEventPublisher eventPublisher;
    private final Map<Long, ConversionSession> userSessions = new ConcurrentHashMap<>();

    public void startWizard(Long chatId, Long userId) {
        ConversionSession session = new ConversionSession();
        userSessions.put(userId, session);

        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("📐 *Unit Converter Wizard*\n\n*Step 1:* Choose physical category:")
                .parseMode("MarkdownV2")
                .replyMarkup(ConversionKeyboardBuilder.buildCategoryKeyboard())
                .build();

        eventPublisher.publishEvent(new MessageEvent(this, message));
    }

    public void handleCallback(Update update) {
        String data = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
        Long userId = update.getCallbackQuery().getFrom().getId();

        ConversionSession session = userSessions.get(userId);
        if (session == null) {
            session = new ConversionSession();
            userSessions.put(userId, session);
        }

        if (data.equals("conv_reset")) {
            userSessions.remove(userId);
            editMessage(chatId, messageId, "🔄 *Wizard reset\\.* Tap `Unit Converter` to start over\\.", null);
            return;
        }

        if (data.equals("conv_back_to_cat")) {
            session.setState(ConversionSession.State.SELECT_CATEGORY);
            editMessage(chatId, messageId,
                    "📐 *Unit Converter Wizard*\n\n*Step 1:* Choose physical category:",
                    ConversionKeyboardBuilder.buildCategoryKeyboard());
            return;
        }

        if (data.equals("conv_back_to_from")) {
            session.setState(ConversionSession.State.SELECT_FROM_UNIT);
            editMessage(chatId, messageId,
                    "📂 Category: *" + UnitConverter.escapeMarkdownV2(session.getSelectedCategory().name()) + "*\n\n*Step 2:* Select unit to convert *FROM*:",
                    ConversionKeyboardBuilder.buildUnitKeyboard(session.getSelectedCategory(), "conv_from_", "conv_back_to_cat"));
            return;
        }

        if (data.startsWith("conv_cat_")) {
            UnitType category = UnitType.valueOf(data.replace("conv_cat_", ""));
            session.setSelectedCategory(category);
            session.setState(ConversionSession.State.SELECT_FROM_UNIT);

            editMessage(chatId, messageId,
                    "📂 Category: *" + UnitConverter.escapeMarkdownV2(category.name()) + "*\n\n*Step 2:* Select unit to convert *FROM*:",
                    ConversionKeyboardBuilder.buildUnitKeyboard(category, "conv_from_", "conv_back_to_cat"));
        } else if (data.startsWith("conv_from_")) {
            Unit fromUnit = Unit.valueOf(data.replace("conv_from_", ""));
            session.setFromUnit(fromUnit);
            session.setState(ConversionSession.State.SELECT_TO_UNIT);

            editMessage(chatId, messageId,
                    "📂 Category: *" + UnitConverter.escapeMarkdownV2(session.getSelectedCategory().name()) + "*\n" +
                            "1️⃣ From: *" + UnitConverter.escapeMarkdownV2(fromUnit.name()) + "*\n\n" +
                            "*Step 3:* Select unit to convert *TO*:",
                    ConversionKeyboardBuilder.buildUnitKeyboard(session.getSelectedCategory(), "conv_to_", "conv_back_to_from"));
        } else if (data.startsWith("conv_to_")) {
            Unit toUnit = Unit.valueOf(data.replace("conv_to_", ""));
            session.setToUnit(toUnit);
            session.setState(ConversionSession.State.ENTER_VALUE);

            editMessage(chatId, messageId,
                    "⚖️ *Selected Conversion:*\n" +
                            "• From: *" + UnitConverter.escapeMarkdownV2(session.getFromUnit().name()) + "*\n" +
                            "• To: *" + UnitConverter.escapeMarkdownV2(toUnit.name()) + "*\n\n" +
                            "✏️ *Step 4:* Type the numerical value in chat \\(e\\.g\\., `100` or `12.5`\\):",
                    null);
        }
    }

    public boolean handleTextInput(Long chatId, Long userId, String input) {
        ConversionSession session = userSessions.get(userId);

        if (session == null || session.getState() != ConversionSession.State.ENTER_VALUE) {
            return false;
        }

        try {
            double value = Double.parseDouble(input.trim());
            double result = UnitConverter.convert(value, session.getFromUnit(), session.getToUnit());

            String formattedResult = (Math.abs(result) < 1e-3 || Math.abs(result) > 1e6)
                    ? String.format("%.4e", result)
                    : String.format("%.4f", result).replaceAll("0+$", "").replaceAll("\\.$", "");

            String response = String.format("✅ *Result:*\n*%s %s* \\= *%s %s*",
                    UnitConverter.escapeMarkdownV2(String.valueOf(value)),
                    UnitConverter.escapeMarkdownV2(session.getFromUnit().name().toLowerCase()),
                    UnitConverter.escapeMarkdownV2(formattedResult),
                    UnitConverter.escapeMarkdownV2(session.getToUnit().name().toLowerCase()));

            SendMessage msg = SendMessage.builder()
                    .chatId(chatId)
                    .text(response)
                    .parseMode("MarkdownV2")
                    .build();

            eventPublisher.publishEvent(new MessageEvent(this, msg));

            userSessions.remove(userId);
            return true;

        } catch (NumberFormatException e) {
            SendMessage errorMsg = SendMessage.builder()
                    .chatId(chatId)
                    .text("⚠️ *Invalid number\\!* Please send a valid number \\(e\\.g\\., `10` or `5.5`\\):")
                    .parseMode("MarkdownV2")
                    .build();

            eventPublisher.publishEvent(new MessageEvent(this, errorMsg));
            return true;
        }
    }

    private void editMessage(Long chatId, Integer messageId, String text, org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup keyboard) {
        EditMessageText edit = EditMessageText.builder()
                .chatId(chatId)
                .messageId(messageId)
                .text(text)
                .parseMode("MarkdownV2")
                .replyMarkup(keyboard)
                .build();

        eventPublisher.publishEvent(new MessageEvent(this, edit));
    }
}