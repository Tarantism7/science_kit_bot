package com.science_kit.science_kit_bot.chemistry.compound;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChemicalCompoundService {
    private final ChemicalCompoundRepository chemicalCompoundRepository;

    public Optional<ChemicalCompound> find(String query){
        if(query == null || query.isBlank()) return Optional.empty();
        query = query.trim();

        Optional<ChemicalCompound> byFormula = chemicalCompoundRepository.findByFormulaIgnoreCase(query);
        if(byFormula.isPresent()) return byFormula;

        return chemicalCompoundRepository.findByNameIgnoreCase(query);
    }

    public List<ChemicalCompound> search(String query){
        if(query == null || query.isBlank()) return List.of();
        return chemicalCompoundRepository.findByFormulaContainingIgnoreCase(query.trim());
    }
}
