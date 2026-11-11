package com.science_kit.science_kit_bot.chemistry.calculator;

import com.science_kit.science_kit_bot.exception.PHCalculationException;
import org.springframework.stereotype.Service;

@Service
public class PHCalculator {

    private static final double MIN_CONCENTRATION = 1e-14;
    private static final double MAX_CONCENTRATION = 1.0;

    public double calculate(double hydrogenConcentration) {

        if (!Double.isFinite(hydrogenConcentration)
                || hydrogenConcentration < MIN_CONCENTRATION
                || hydrogenConcentration > MAX_CONCENTRATION) {

            throw new PHCalculationException(
                    "Concentration must be between 1e-14 and 1.0 mol/L."
            );
        }

        return -Math.log10(hydrogenConcentration);
    }
}