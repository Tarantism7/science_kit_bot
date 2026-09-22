package com.science_kit.science_kit_bot.bot.service;

import com.science_kit.science_kit_bot.bot.model.PhysicsFormula;
import com.science_kit.science_kit_bot.bot.repository.PhysicsFormulaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhysicsFormulaService {

    private final PhysicsFormulaRepository repository;

    public List<PhysicsFormula> search(String query) {
        return repository.findByNameContainingIgnoreCase(query);
    }

    public List<PhysicsFormula> getByCategory(String category) {
        return repository.findByCategoryIgnoreCase(category);
    }

    public List<PhysicsFormula> getAll() {
        return repository.findAll();
    }
}
