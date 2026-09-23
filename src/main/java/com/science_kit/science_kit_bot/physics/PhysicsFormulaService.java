package com.science_kit.science_kit_bot.physics;

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
