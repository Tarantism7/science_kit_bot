
package com.science_kit.science_kit_bot.chemistry.calculator;

import com.science_kit.science_kit_bot.element.Element;
import com.science_kit.science_kit_bot.element.ElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MolarMassCalculator {

    private final ChemicalFormulaParser parser;
    private final ElementService elementService;

    public BigDecimal calculate(String formula) {

        Map<String, Integer> atoms = parser.parse(formula);

        BigDecimal totalMass = BigDecimal.ZERO;

        for (Map.Entry<String, Integer> atom : atoms.entrySet()) {

            Element element = elementService.find(atom.getKey())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Unknown element: " + atom.getKey()
                    ));

            if (element.getAtomicMass() == null) {
                throw new IllegalArgumentException(
                        "Atomic mass unavailable for: " + atom.getKey()
                );
            }

            BigDecimal elementMass = element.getAtomicMass()
                    .multiply(BigDecimal.valueOf(atom.getValue()));

            totalMass = totalMass.add(elementMass);
        }

        return totalMass.setScale(4, RoundingMode.HALF_UP);
    }
}