package com.science_kit.science_kit_bot.converter;

import java.util.*;

/**
 * Central conversion utility defining physical units, their relationships,
 * alias parsing, category matching, and conversion math.
 */
public class UnitConverter {

    // Private constructor prevents instantiation of this utility class.
    private UnitConverter() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static double convert(double value, Unit from, Unit to) {
        if (from.getType() != to.getType()) {
            throw new IllegalArgumentException(
                    String.format("Cannot convert between mismatched unit types: %s (%s) -> %s (%s)",
                            from.name(), from.getType(), to.name(), to.getType())
            );
        }

        if (from == to) {
            return value;
        }

        return to.convertFromBase(from.convertToBase(value));
    }

    /**
     * Overloaded method defaulting to full directory generation.
     */
    public static String generateHelpMessage() {
        return generateHelpMessage(null);
    }

    public static String generateHelpMessage(String targetCategory) {
        StringBuilder sb = new StringBuilder();

        UnitType singleTargetType = null;
        if (targetCategory != null && !targetCategory.isBlank()) {
            singleTargetType = UnitType.parseCategory(targetCategory);
            if (singleTargetType == null) {
                return String.format("❌ Unknown unit category: *'%s'*\n\nType `/convert help` to see all supported categories\\.",
                        escapeMarkdownV2(targetCategory));
            }
        }

        if (singleTargetType != null) {
            sb.append("📏 *Supported Units for ").append(escapeMarkdownV2(formatTypeName(singleTargetType))).append("*\n\n");
        } else {
            sb.append("📏 *Supported Measurement Units*\n\n");
        }

        Map<UnitType, List<Unit>> grouped = new EnumMap<>(UnitType.class);
        for (UnitType t : UnitType.values()) {
            grouped.put(t, new ArrayList<>());
        }
        for (Unit u : Unit.values()) {
            grouped.get(u.getType()).add(u);
        }

        for (Map.Entry<UnitType, List<Unit>> entry : grouped.entrySet()) {
            if (singleTargetType != null && entry.getKey() != singleTargetType) {
                continue;
            }

            if (entry.getValue().isEmpty()) continue;

            sb.append("🔹 *").append(escapeMarkdownV2(formatTypeName(entry.getKey()))).append("*\n");
            for (Unit u : entry.getValue()) {
                sb.append("• `").append(u.name().toLowerCase()).append("`");
                if (!u.getAliases().isEmpty()) {
                    String escapedAliases = escapeMarkdownV2(String.join(", ", u.getAliases()));
                    sb.append(" \\(aliases: ").append(escapedAliases).append("\\)");
                }
                sb.append("\n");
            }
            sb.append("\n");
        }

        if (singleTargetType == null) {
            sb.append("💡 *Usage Examples:*\n");
            sb.append("• `/convert help mass`\n");
            sb.append("• `/convert 100 c f`\n");
            sb.append("• `/convert 10 km to mi`\n");
        } else {
            sb.append("💡 *Usage Example:*\n");
            Unit exampleUnit = grouped.get(singleTargetType).get(0);
            sb.append("• `/convert 10 ").append(exampleUnit.name().toLowerCase()).append(" to <target\\_unit>`\n");
        }

        return sb.toString();
    }

    private static String formatTypeName(UnitType type) {
        String[] words = type.name().split("_");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            sb.append(w.charAt(0)).append(w.substring(1).toLowerCase()).append(" ");
        }
        return sb.toString().trim();
    }

    public static String escapeMarkdownV2(String text) {
        if (text == null) return "";
        return text.replaceAll("([_*\\[\\]()~`>#+\\-=|{}.!\\\\])", "\\\\$1");
    }
}