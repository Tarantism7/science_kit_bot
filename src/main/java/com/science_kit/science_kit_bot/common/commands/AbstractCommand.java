package com.science_kit.science_kit_bot.common.commands;

import com.science_kit.science_kit_bot.common.utils.CommandUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class AbstractCommand implements Command {

    private final CommandName commandName;

    protected AbstractCommand(CommandName commandName) {
        this.commandName = commandName;
    }

    @Override
    public boolean canHandle(Update update) {
        return CommandUtils.canHandleCommand(update, getCommand());
    }

    @Override
    public String getCommand() {
        return commandName.getCommand();
    }
}