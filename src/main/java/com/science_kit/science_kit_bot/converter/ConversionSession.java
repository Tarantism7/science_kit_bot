package com.science_kit.science_kit_bot.converter;

import lombok.Data;

@Data
public class ConversionSession {

    public enum State {
        SELECT_CATEGORY,
        SELECT_FROM_UNIT,
        SELECT_TO_UNIT,
        ENTER_VALUE
    }

    private State state = State.SELECT_CATEGORY;
    private UnitType selectedCategory;
    private Unit fromUnit;
    private Unit toUnit;
}