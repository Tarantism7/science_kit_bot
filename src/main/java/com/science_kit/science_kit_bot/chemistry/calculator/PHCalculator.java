package com.science_kit.science_kit_bot.chemistry.calculator;

import org.springframework.stereotype.Service;

@Service
public class PHCalculator {

    private static final double MIN_CONCENTRATION = 1e-14;
    private static final double MAX_CONCENTRATION = 1.0;

    public double calculate(double hydrogenConcentration) {

        if (!Double.isFinite(hydrogenConcentration)
                || hydrogenConcentration <= 0
                || hydrogenConcentration > MAX_CONCENTRATION) {
            throw new IllegalArgumentException(
                    "Concentration must be greater than 0 and at most 1 mol/L."
            );
        }

        return -Math.log10(hydrogenConcentration);
    }
}