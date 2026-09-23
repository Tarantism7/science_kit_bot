package com.science_kit.science_kit_bot.element;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "elements")
public class Element {

    @Id
    @Column(name = "atomic_number")
    private Integer atomicNumber;

    @Column(nullable = false, unique = true, length = 3)
    private String symbol;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "atomic_mass")
    private BigDecimal atomicMass;

    private Integer period;

    @Column(name = "group_number")
    private Integer groupNumber;

    private String category;

    @Column(name = "electron_configuration")
    private String electronConfiguration;

    private BigDecimal electronegativity;

    @Column(name = "state_at_room_temperature")
    private String stateAtRoomTemperature;

    @Column(name = "density_g_cm3")
    private BigDecimal densityGcm3;

    @Column(name = "melting_point_k")
    private BigDecimal meltingPointK;

    @Column(name = "boiling_point_k")
    private BigDecimal boilingPointK;

    @Column(name = "oxidation_states")
    private String oxidationStates;
}