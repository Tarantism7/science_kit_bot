package com.science_kit.science_kit_bot.chemistry.calculator;

import org.springframework.stereotype.Service;

@Service
public class MolarityCalculator {

    public double calculate(double moles, double volumeLiters) {

        if (!Double.isFinite(moles) || moles < 0) {
            throw new IllegalArgumentException(
                    "Moles must be non-negative."
            );
        }

        if (!Double.isFinite(volumeLiters) || volumeLiters <= 0) {
            throw new IllegalArgumentException(
                    "Volume must be greater than zero."
            );
        }

        return moles / volumeLiters;
    }
}