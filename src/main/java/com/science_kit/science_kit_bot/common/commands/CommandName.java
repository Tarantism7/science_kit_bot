package com.science_kit.science_kit_bot.common.commands;

import lombok.Getter;

@Getter
public enum CommandName {
    ABOUT("ABOUT_COMMAND","/about"),
    LANGUAGE("LANGUAGE_COMMAND","/language"),
    START("START_COMMAND","/start"),
    CONVERT("CONVERT_COMMAND","/convert"),
    ELEMENT("ELEMENT_COMMAND","/elem"),
    INTERACTIVE_CONVERTER("INTERACTIVE_CONVERTER_COMMAND","/interactive_converter"),
    CHEM_FORMULA("CHEMICAL_FORMULA_COMMAND","/cf"),
    CHEM_COMPOUND("CHEMICAL_COMPOUND_COMMAND","/cc"),
    MOLAR_MASS("MOLAR_MASS_COMMAND","/molar"),
    MOLARITY("MOLARITY_COMMAND","/molarity"),
    PH("PH_COMMAND","/ph"),;

    private final String beanName;
    private final String command;

    CommandName(String beanName, String command) {
        this.beanName = beanName;
        this.command = command;
    }
}
