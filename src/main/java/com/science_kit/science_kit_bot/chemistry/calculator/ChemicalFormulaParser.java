
package com.science_kit.science_kit_bot.chemistry.calculator;

import com.science_kit.science_kit_bot.element.ElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ChemicalFormulaParser {

    private final ElementService elementService;

    public Map<String, Integer> parse(String formula) {
        if (formula == null || formula.isBlank()) {
            throw new IllegalArgumentException("Formula cannot be empty.");
        }

        Parser parser = new Parser(formula.trim());

        Map<String, Integer> result = parser.parseGroup(false);

        if (!parser.isFinished()) {
            throw new IllegalArgumentException("Invalid chemical formula.");
        }

        return result;
    }

    private class Parser {

        private final String input;
        private int position = 0;

        private Parser(String input) {
            this.input = input;
        }

        private Map<String, Integer> parseGroup(boolean nested) {
            Map<String, Integer> atoms = new HashMap<>();

            while (position < input.length()) {
                char current = input.charAt(position);

                if (current == ')') {
                    if (!nested) {
                        throw new IllegalArgumentException(
                                "Unexpected closing parenthesis."
                        );
                    }

                    position++;
                    return atoms;
                }

                if (current == '(') {
                    position++;

                    Map<String, Integer> group =
                            parseGroup(true);

                    int multiplier = parseNumber();

                    group.forEach((symbol, count) ->
                            atoms.merge(
                                    symbol,
                                    Math.multiplyExact(count, multiplier),
                                    Math::addExact
                            )
                    );

                } else if (Character.isUpperCase(current)) {

                    String symbol = parseSymbol();

                    int count = parseNumber();

                    atoms.merge(symbol, count, Math::addExact);

                } else {
                    throw new IllegalArgumentException(
                            "Invalid character at position " + position
                    );
                }
            }

            if (nested) {
                throw new IllegalArgumentException(
                        "Missing closing parenthesis."
                );
            }

            return atoms;
        }

        private String parseSymbol() {
            int start = position++;

            if (position < input.length()
                    && Character.isLowerCase(input.charAt(position))) {
                position++;
            }

            return input.substring(start, position);
        }

        private int parseNumber() {
            int start = position;

            while (position < input.length()
                    && Character.isDigit(input.charAt(position))) {
                position++;
            }

            if (start == position) {
                return 1;
            }

            int number = Integer.parseInt(
                    input.substring(start, position)
            );

            if (number < 1) {
                throw new IllegalArgumentException(
                        "Atom count must be positive."
                );
            }

            return number;
        }

        private boolean isFinished() {
            return position == input.length();
        }
    }
}