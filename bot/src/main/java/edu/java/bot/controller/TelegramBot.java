package edu.java.bot.controller;

import com.pengrad.telegrambot.UpdatesListener;

public class TelegramBot extends com.pengrad.telegrambot.TelegramBot {

    public TelegramBot(String botToken) {
        super(botToken);
    }

    private CommandHandler handler = new CommandHandler();

    public void run() {
        this.setUpdatesListener(updates -> {
                updates.forEach(update -> execute(handler.handle(update)));
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        );
    }

}

