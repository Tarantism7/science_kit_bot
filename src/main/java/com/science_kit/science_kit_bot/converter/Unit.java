package com.science_kit.science_kit_bot.converter;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public enum Unit {

    // --- LENGTH (Base Unit: METER) ---
    NANOMETER(UnitType.LENGTH, 1e-9, "nm"),
    MICROMETER(UnitType.LENGTH, 1e-6, "um", "μm"),
    MILLIMETER(UnitType.LENGTH, 0.001, "mm"),
    CENTIMETER(UnitType.LENGTH, 0.01, "cm"),
    METER(UnitType.LENGTH, 1.0, "m"),
    KILOMETER(UnitType.LENGTH, 1000.0, "km"),
    INCH(UnitType.LENGTH, 0.0254, "in", "inch", "inches"),
    FOOT(UnitType.LENGTH, 0.3048, "ft", "feet"),
    YARD(UnitType.LENGTH, 0.9144, "yd", "yard", "yards"),
    MILE(UnitType.LENGTH, 1609.344, "mi", "mile", "miles"),
    NAUTICAL_MILE(UnitType.LENGTH, 1852.0, "nmi"),
    ASTRONOMICAL_UNIT(UnitType.LENGTH, 1.495978707e11, "au"),
    LIGHT_YEAR(UnitType.LENGTH, 9.4607304725808e15, "ly"),
    PARSEC(UnitType.LENGTH, 3.08567758149137e16, "pc"),

    // --- MASS (Base Unit: KILOGRAM) ---
    MILLIGRAM(UnitType.MASS, 1e-6, "mg"),
    GRAM(UnitType.MASS, 0.001, "g"),
    KILOGRAM(UnitType.MASS, 1.0, "kg"),
    TONNE(UnitType.MASS, 1000.0, "t", "ton"),
    OUNCE(UnitType.MASS, 0.028349523125, "oz"),
    POUND(UnitType.MASS, 0.45359237, "lb", "lbs"),
    STONE(UnitType.MASS, 6.35029318, "st"),

    // --- VOLUME (Base Unit: LITER) ---
    MILLILITER(UnitType.VOLUME, 0.001, "ml"),
    LITER(UnitType.VOLUME, 1.0, "l"),
    CUBIC_METER(UnitType.VOLUME, 1000.0, "m3"),
    GALLON_US(UnitType.VOLUME, 3.785411784, "gal"),
    QUART_US(UnitType.VOLUME, 0.946352946, "qt"),
    PINT_US(UnitType.VOLUME, 0.473176473, "pt"),
    CUP_US(UnitType.VOLUME, 0.24, "cup"),
    FLUID_OUNCE_US(UnitType.VOLUME, 0.0295735295625, "floz"),

    // --- TEMPERATURE (Base Unit: KELVIN) ---
    KELVIN(UnitType.TEMPERATURE, k -> k, k -> k, "k"),
    CELSIUS(UnitType.TEMPERATURE, c -> c + 273.15, k -> k - 273.15, "c", "°c"),
    FAHRENHEIT(UnitType.TEMPERATURE, f -> (f - 32.0) * 5.0 / 9.0 + 273.15, k -> (k - 273.15) * 9.0 / 5.0 + 32.0, "f", "°f"),

    // --- DATA STORAGE (Base Unit: BYTE) ---
    BIT(UnitType.DATA_STORAGE, 0.125, "b"),
    BYTE(UnitType.DATA_STORAGE, 1.0, "B"),
    KILOBYTE(UnitType.DATA_STORAGE, 1e3, "kb"),
    MEGABYTE(UnitType.DATA_STORAGE, 1e6, "mb"),
    GIGABYTE(UnitType.DATA_STORAGE, 1e9, "gb"),
    TERABYTE(UnitType.DATA_STORAGE, 1e12, "tb"),
    PETABYTE(UnitType.DATA_STORAGE, 1e15, "pb"),
    KIBIBYTE(UnitType.DATA_STORAGE, 1024.0, "kib"),
    MEBIBYTE(UnitType.DATA_STORAGE, 1048576.0, "mib"),
    GIBIBYTE(UnitType.DATA_STORAGE, 1073741824.0, "gib"),
    TEBIBYTE(UnitType.DATA_STORAGE, 1099511627776.0, "tib"),

    // --- PRESSURE (Base Unit: PASCAL) ---
    PASCAL(UnitType.PRESSURE, 1.0, "pa"),
    KILOPASCAL(UnitType.PRESSURE, 1000.0, "kpa"),
    BAR(UnitType.PRESSURE, 100000.0, "bar"),
    MILLIBAR(UnitType.PRESSURE, 100.0, "mbar"),
    ATMOSPHERE(UnitType.PRESSURE, 101325.0, "atm"),
    PSI(UnitType.PRESSURE, 6894.757293168, "psi"),
    TORR(UnitType.PRESSURE, 133.322368421, "torr"),

    // --- ENERGY (Base Unit: JOULE) ---
    JOULE(UnitType.ENERGY, 1.0, "j"),
    KILOJOULE(UnitType.ENERGY, 1000.0, "kj"),
    CALORIE(UnitType.ENERGY, 4.184, "cal"),
    KILOCALORIE(UnitType.ENERGY, 4184.0, "kcal"),
    WATT_HOUR(UnitType.ENERGY, 3600.0, "wh"),
    KILOWATT_HOUR(UnitType.ENERGY, 3600000.0, "kwh"),
    ELECTRON_VOLT(UnitType.ENERGY, 1.602176634e-19, "ev"),

    // --- POWER (Base Unit: WATT) ---
    MILLIWATT(UnitType.POWER, 0.001, "mw"),
    WATT(UnitType.POWER, 1.0, "w"),
    KILOWATT(UnitType.POWER, 1000.0, "kw"),
    MEGAWATT(UnitType.POWER, 1e6, "MW"),
    HORSEPOWER(UnitType.POWER, 745.6998715822702, "hp"),

    // --- SPEED (Base Unit: METER_PER_SECOND) ---
    METER_PER_SECOND(UnitType.SPEED, 1.0, "m/s", "mps"),
    KILOMETER_PER_HOUR(UnitType.SPEED, 1.0 / 3.6, "km/h", "kmh", "kph"),
    MILE_PER_HOUR(UnitType.SPEED, 0.44704, "mph"),
    FOOT_PER_SECOND(UnitType.SPEED, 0.3048, "ft/s", "fps"),
    KNOT(UnitType.SPEED, 0.514444444444, "kt", "knots"),

    // --- ELECTRIC CURRENT (Base Unit: AMPERE) ---
    MICROAMPERE(UnitType.ELECTRIC_CURRENT, 1e-6, "ua", "μa"),
    MILLIAMPERE(UnitType.ELECTRIC_CURRENT, 0.001, "ma"),
    AMPERE(UnitType.ELECTRIC_CURRENT, 1.0, "a", "amp"),
    KILOAMPERE(UnitType.ELECTRIC_CURRENT, 1000.0, "ka"),

    // --- ELECTRIC POTENTIAL (Base Unit: VOLT) ---
    MILLIVOLT(UnitType.ELECTRIC_POTENTIAL, 0.001, "mv"),
    VOLT(UnitType.ELECTRIC_POTENTIAL, 1.0, "v"),
    KILOVOLT(UnitType.ELECTRIC_POTENTIAL, 1000.0, "kv"),

    // --- TIME (Base Unit: SECOND) ---
    NANOSECOND(UnitType.TIME, 1e-9, "ns"),
    MICROSECOND(UnitType.TIME, 1e-6, "us", "μs"),
    MILLISECOND(UnitType.TIME, 0.001, "ms"),
    SECOND(UnitType.TIME, 1.0, "s", "sec"),
    MINUTE(UnitType.TIME, 60.0, "min"),
    HOUR(UnitType.TIME, 3600.0, "h", "hr"),
    DAY(UnitType.TIME, 86400.0, "d", "day", "days"),

    // --- FREQUENCY (Base Unit: HERTZ) ---
    HERTZ(UnitType.FREQUENCY, 1.0, "hz"),
    KILOHERTZ(UnitType.FREQUENCY, 1000.0, "khz"),
    MEGAHERTZ(UnitType.FREQUENCY, 1e6, "mhz"),
    GIGAHERTZ(UnitType.FREQUENCY, 1e9, "ghz"),

    // --- AMOUNT OF SUBSTANCE (Base Unit: MOLE) ---
    NANOMOLE(UnitType.AMOUNT_OF_SUBSTANCE, 1e-9, "nmol"),
    MICROMOLE(UnitType.AMOUNT_OF_SUBSTANCE, 1e-6, "umol", "μmol"),
    MILLIMOLE(UnitType.AMOUNT_OF_SUBSTANCE, 0.001, "mmol"),
    MOLE(UnitType.AMOUNT_OF_SUBSTANCE, 1.0, "mol"),

    // --- CONCENTRATION (Base Unit: MOLAR) ---
    MICROMOLAR(UnitType.CONCENTRATION, 1e-6, "umolar", "μM"),
    MILLIMOLAR(UnitType.CONCENTRATION, 0.001, "mmolar", "mM"),
    MOLAR(UnitType.CONCENTRATION, 1.0, "molar"),

    // --- AREA (Base Unit: SQUARE_METER) ---
    SQUARE_CENTIMETER(UnitType.AREA, 0.0001, "cm2"),
    SQUARE_METER(UnitType.AREA, 1.0, "m2"),
    SQUARE_KILOMETER(UnitType.AREA, 1e6, "km2"),
    ACRE(UnitType.AREA, 4046.8564224, "acre", "acres"),
    HECTARE(UnitType.AREA, 10000.0, "ha"),
    SQUARE_FOOT(UnitType.AREA, 0.09290304, "ft2", "sqft"),

    // --- FORCE (Base Unit: NEWTON) ---
    DYNE(UnitType.FORCE, 1e-5, "dyn"),
    NEWTON(UnitType.FORCE, 1.0, "n"),
    KILONEWTON(UnitType.FORCE, 1000.0, "kn"),
    POUND_FORCE(UnitType.FORCE, 4.44822161526, "lbf"),

    // --- TORQUE (Base Unit: NEWTON_METER) ---
    NEWTON_METER(UnitType.TORQUE, 1.0, "nm"),
    FOOT_POUND(UnitType.TORQUE, 1.355817948331, "ftlb", "ft-lb"),

    // --- RESISTANCE (Base Unit: OHM) ---
    OHM(UnitType.RESISTANCE, 1.0, "ohm", "Ω"),
    KILOHM(UnitType.RESISTANCE, 1000.0, "kohm", "kΩ"),
    MEGOHM(UnitType.RESISTANCE, 1e6, "mohm", "MΩ"),

    // --- CAPACITANCE (Base Unit: FARAD) ---
    PICOFARAD(UnitType.CAPACITANCE, 1e-12, "pf"),
    MICROFARAD(UnitType.CAPACITANCE, 1e-6, "uf", "μf"),
    FARAD(UnitType.CAPACITANCE, 1.0, "farad"),

    // --- MAGNETIC FLUX DENSITY (Base Unit: TESLA) ---
    GAUSS(UnitType.MAGNETIC_FLUX_DENSITY, 1e-4, "g", "gauss"),
    TESLA(UnitType.MAGNETIC_FLUX_DENSITY, 1.0, "t_tesla"),

    // --- ANGLE (Base Unit: RADIAN) ---
    RADIAN(UnitType.ANGLE, 1.0, "rad"),
    DEGREE(UnitType.ANGLE, Math.PI / 180.0, "deg", "degree", "degrees"),
    GRADIAN(UnitType.ANGLE, Math.PI / 200.0, "grad");

    private final UnitType type;
    private final DoubleUnaryOperator toBase;
    private final DoubleUnaryOperator fromBase;
    private final List<String> aliases;

    private static final Map<String, Unit> ALIAS_MAP = new HashMap<>();

    static {
        for (Unit u : values()) {
            ALIAS_MAP.put(u.name().toLowerCase(), u);
            for (String alias : u.aliases) {
                ALIAS_MAP.put(alias.toLowerCase(), u);
            }
        }
    }

    Unit(UnitType type, double factorToBase, String... aliases) {
        this(type, val -> val * factorToBase, baseVal -> baseVal / factorToBase, aliases);
    }

    Unit(UnitType type, DoubleUnaryOperator toBase, DoubleUnaryOperator fromBase, String... aliases) {
        this.type = type;
        this.toBase = toBase;
        this.fromBase = fromBase;
        this.aliases = Arrays.asList(aliases);
    }

    public double convertToBase(double value) {
        return toBase.applyAsDouble(value);
    }

    public double convertFromBase(double baseValue) {
        return fromBase.applyAsDouble(baseValue);
    }

    public static Unit parse(String input) {
        if (input == null || input.isBlank()) {
            return null;
        }

        String sanitized = input.trim().toLowerCase();

        Unit matchedUnit = ALIAS_MAP.get(sanitized);
        if (matchedUnit != null) {
            return matchedUnit;
        }

        if (sanitized.endsWith("s") && sanitized.length() > 1) {
            String singular = sanitized.substring(0, sanitized.length() - 1);
            matchedUnit = ALIAS_MAP.get(singular);
            if (matchedUnit != null) {
                return matchedUnit;
            }
        }

        if (sanitized.endsWith("es") && sanitized.length() > 2) {
            String singular = sanitized.substring(0, sanitized.length() - 2);
            return ALIAS_MAP.get(singular);
        }

        return null;
    }
}
