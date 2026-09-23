package com.science_kit.science_kit_bot.element;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ElementRepository extends JpaRepository<Element, Integer> {
    Optional<Element> findBySymbolIgnoreCase(String symbol);
    Optional<Element> findByNameIgnoreCase(String name);
    Optional<Element> findByAtomicNumber(Integer atomicNumber);
}
