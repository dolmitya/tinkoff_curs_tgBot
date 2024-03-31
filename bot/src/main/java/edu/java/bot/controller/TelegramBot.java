package edu.java.bot.controller;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.configuration.ApplicationConfig;

public class TelegramBot extends com.pengrad.telegrambot.TelegramBot {

    private CommandHandler handler;

    public TelegramBot(ApplicationConfig botToken, ScrapperClient scrapperClient) {
        super(botToken.telegramToken());
        handler = new CommandHandler(scrapperClient);
    }

    public void sendMessage(SendMessage sendMessage) {
        execute(sendMessage);
    }

    public void run() {
        this.setUpdatesListener(updates -> {
                updates.forEach(update -> execute(handler.handle(update)));
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        );
    }

}

