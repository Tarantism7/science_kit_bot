package com.science_kit.science_kit_bot.bot.model;

import com.science_kit.science_kit_bot.bot.commands.InteractiveUnitType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class UserSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id")
    private Long chatId;

    private String username;

    private String locale;

    @Enumerated(EnumType.STRING)
    private InteractiveUnitType sourceUnit;

    @Enumerated(EnumType.STRING)
    private InteractiveUnitType targetUnit;

    private Double primaryValue;
}
