package com.science_kit.science_kit_bot.converter;

import java.util.Arrays;
import java.util.List;

/**
 * Categorizes units into mutually compatible physical dimensions.
 * Includes category aliases so users can search with terms like "weight" or "temp".
 */
public enum UnitType {
    LENGTH("length", "distance", "len"),
    MASS("mass", "weight"),
    VOLUME("volume", "vol"),
    TEMPERATURE("temperature", "temp"),
    DATA_STORAGE("data_storage", "data", "storage", "bytes"),
    PRESSURE("pressure", "press"),
    ENERGY("energy", "work"),
    POWER("power"),
    SPEED("speed", "velocity"),
    ELECTRIC_CURRENT("electric_current", "current", "ampere"),
    ELECTRIC_POTENTIAL("electric_potential", "voltage", "volts"),
    TIME("time", "duration"),
    FREQUENCY("frequency", "freq"),
    AMOUNT_OF_SUBSTANCE("amount_of_substance", "mole", "substance"),
    CONCENTRATION("concentration", "molarity"),
    AREA("area", "surface"),
    FORCE("force"),
    TORQUE("torque"),
    RESISTANCE("resistance", "ohm"),
    CAPACITANCE("capacitance", "farad"),
    MAGNETIC_FLUX_DENSITY("magnetic_flux_density", "magnetic", "tesla"),
    ANGLE("angle", "angles");

    private final List<String> aliases;

    UnitType(String... aliases) {
        this.aliases = Arrays.asList(aliases);
    }

    /**
     * Resolves user input (e.g., "mass", "weight", "temp") to a UnitType enum.
     * Returns null if no matching category is found.
     */
    public static UnitType parseCategory(String input) {
        if (input == null || input.isBlank()) return null;
        String sanitized = input.trim().toLowerCase().replace(" ", "_");

        for (UnitType type : values()) {
            if (type.name().toLowerCase().equals(sanitized) || type.aliases.contains(sanitized)) {
                return type;
            }
        }
        return null;
    }
}
