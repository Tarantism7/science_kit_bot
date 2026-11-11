package com.science_kit.science_kit_bot.exception;

import com.science_kit.science_kit_bot.converter.Unit;

public class UnitConversionException extends BotException{
    public UnitConversionException(String message) {super(message);}
    public UnitConversionException(String message, Throwable cause) {super(message,cause);}
    public UnitConversionException(Unit from, Unit to) {
        super(String.format(
                "Cannot convert between mismatched unit types: %s (%s) -> %s (%s)",
                from.name(),
                from.getType(),
                to.name(),
                to.getType()
        ));
    }
}
