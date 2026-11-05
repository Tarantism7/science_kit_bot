package com.science_kit.science_kit_bot.rust;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Map;

public class RustClientDto {

    public record MolarMassRequest(
            String formula,
            @JsonProperty("atomic_masses")
            Map<String, Double> atomicMasses
    ) {}

    public record ElementBreakdown(
            String symbol,
            int count,
            @JsonProperty("atomic_mass")
            double atomicMass,
            double contribution
    ) {}

    public record MolarMassResponse(
            String formula,
            @JsonProperty("molar_mass")
            double molarMass,
            String unit,
            List<ElementBreakdown> breakdown
    ) {}
}