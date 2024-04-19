package edu.java.bot.controller;

import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.configuration.ApplicationConfig;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.composite.CompositeMeterRegistry;

public class TelegramBot extends com.pengrad.telegrambot.TelegramBot {

    private final CommandHandler handler;
    private final Counter counter;

    public TelegramBot(
        ApplicationConfig botToken,
        ScrapperClient scrapperClient,
        CompositeMeterRegistry meterRegistry
    ) {
        super(botToken.telegramToken());
        handler = new CommandHandler(scrapperClient);
        counter =
            Counter.builder("processed_messages").tag("application", "bot").register(meterRegistry);
    }

    public void sendMessage(SendMessage sendMessage) {
        execute(sendMessage);
    }

    public void run() {
        this.setUpdatesListener(updates -> {
                updates.forEach(update -> execute(handler.handle(update)));
                counter.increment();
                return UpdatesListener.CONFIRMED_UPDATES_ALL;
            }
        );
    }

}

