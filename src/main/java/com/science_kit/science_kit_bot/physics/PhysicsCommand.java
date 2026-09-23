package com.science_kit.science_kit_bot.physics;

import com.science_kit.science_kit_bot.common.commands.Command;
import org.telegram.telegrambots.meta.api.objects.Update;

public class PhysicsCommand implements Command {
    @Override
    public boolean canHandle(Update update) {
        return update.hasMessage()
                && update.getMessage().hasText()
                && update.getMessage().getText().startsWith("/physics");
    }

    @Override
    public void handle(Update update) {

    }

    @Override
    public String getCommand() {
        return "";
    }
}
