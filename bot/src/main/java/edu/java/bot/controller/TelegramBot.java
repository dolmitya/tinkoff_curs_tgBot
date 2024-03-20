package edu.java.bot.controller;

import com.pengrad.telegrambot.UpdatesListener;
import edu.java.bot.users.Users;
import org.springframework.stereotype.Component;

public class TelegramBot extends com.pengrad.telegrambot.TelegramBot {

    public TelegramBot(String botToken) {
        super(botToken);
    }

    Users users = new Users();

    private CommandHandler handler = new CommandHandler();

    public void run() {
        this.setUpdatesListener(updates -> {
                updates.forEach(update -> execute(handler.handle(update, users)));
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        );
    }

}

