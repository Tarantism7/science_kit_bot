package com.science_kit.science_kit_bot.bot.repository;

import com.science_kit.science_kit_bot.bot.model.PhysicsFormula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhysicsFormulaRepository extends JpaRepository<PhysicsFormula, Long> {
    List<PhysicsFormula> findByCategoryIgnoreCase(String category);
    List<PhysicsFormula> findByNameContainingIgnoreCase(String name);
    List<PhysicsFormula> findByKeywordsContainingIgnoreCase(String keyword);
}
