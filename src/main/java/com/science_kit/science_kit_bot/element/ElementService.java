package com.science_kit.science_kit_bot.element;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ElementService {
    private final ElementRepository elementRepository;

    public Optional<Element> find(String query){
        query = query.trim();

        try{
            int atomicNumber = Integer.parseInt(query);
            if(atomicNumber < 1 || atomicNumber > 118){
                return Optional.empty();
            }
            return elementRepository.findById(atomicNumber);
        } catch (NumberFormatException e){
            return elementRepository.findBySymbolIgnoreCase(query);
        }
    }
}
