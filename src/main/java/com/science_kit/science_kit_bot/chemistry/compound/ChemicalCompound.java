package com.science_kit.science_kit_bot.chemistry.compound;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chemical_compounds")
public class ChemicalCompound {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, unique = true, length = 100)
    private String formula;

    @Column(name = "molar_mass", precision = 12, scale = 6)
    private BigDecimal molarMass;

    @Column(name = "compound_type", length = 100)
    private String compoundType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String appearance;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createAt;

    @PrePersist
    protected  void onCreate() {
        if(createAt == null) {
            createAt = LocalDateTime.now();
        }
    }
}
