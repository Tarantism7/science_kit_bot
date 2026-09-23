package com.science_kit.science_kit_bot.converter;

import lombok.Getter;

@Getter
public enum InteractiveUnitType {
    AMPERE("unit.ampere"),
    VOLT("unit.volt"),
    WATT("unit.watt");

    private final String messageKey;

    InteractiveUnitType(String messageKey) {
        this.messageKey = messageKey;
    }

    public String getCallbackData() {
        return name().toLowerCase();
    }

    public static InteractiveUnitType fromCallback(String callbackData) {
        return valueOf(callbackData.toUpperCase());
    }
}
