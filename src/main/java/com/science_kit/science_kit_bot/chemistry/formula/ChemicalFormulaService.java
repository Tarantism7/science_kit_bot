package com.science_kit.science_kit_bot.chemistry.formula;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChemicalFormulaService {
    private final ChemistryFormulaRepository chemistryFormulaRepository;

    public List<ChemistryFormula> search(String query) {
        if (query == null || query.isBlank()) {
            return List.of();
        }
        return chemistryFormulaRepository.search(query.trim());
    }

    public List<ChemistryFormula> findByCategory(String category) {
        return chemistryFormulaRepository.findByCategoryIgnoreCase(category.trim());
    }
}
