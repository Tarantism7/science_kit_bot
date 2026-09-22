package com.science_kit.science_kit_bot.bot.commands;

import lombok.Getter;

@Getter
public enum CommandName {
    ABOUT("ABOUT_COMMAND"),
    LANGUAGE("LANGUAGE_COMMAND"),
    START("START_COMMAND"),
    CONVERT("CONVERT_COMMAND"),
    ELEMENT("ELEMENT_COMMAND"),
    INTERACTIVE_CONVERTER("INTERACTIVE_CONVERTER_COMMAND");

    private final String commandName;

    CommandName(String commandName) {
        this.commandName = commandName;
    }
}
