package com.science_kit.science_kit_bot.chemistry.formula;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "chemistry_formulas")
public class ChemistryFormula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String category;

    @Column(nullable = false, length = 200)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String formula;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String variables;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String units;

    @Column(columnDefinition = "TEXT")
    private String keywords;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
