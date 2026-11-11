package com.science_kit.science_kit_bot.chemistry.compound;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ChemicalCompoundRepository extends JpaRepository<ChemicalCompound, Long> {
    Optional<ChemicalCompound> findByFormulaIgnoreCase(String formula);
    Optional<ChemicalCompound> findByNameIgnoreCase(String name);
    List<ChemicalCompound> findByNameContainingIgnoreCase(String name);
    List<ChemicalCompound> findByFormulaContainingIgnoreCase(String formula);
}
