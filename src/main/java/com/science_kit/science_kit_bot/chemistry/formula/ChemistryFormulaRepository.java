package com.science_kit.science_kit_bot.chemistry.formula;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChemistryFormulaRepository extends JpaRepository<ChemistryFormula, Long> {
    List<ChemistryFormula> findByCategoryIgnoreCase(String category);
    List<ChemistryFormula> findByNameContainingIgnoreCase(String name);

    @Query(value = """
            SELECT *
            FROM chemistry_formulas
            WHERE name ILIKE CONCAT('%', :query, '%')
                OR category ILIKE CONCAT('%', :query, '%')
                OR keywords ILIKE CONCAT('%', :query, '%')
                OR description ILIKE CONCAT ('%', :query, '%')
            ORDER BY category, name
            """, nativeQuery = true)
    List<ChemistryFormula> search(@Param("query") String query);
}
