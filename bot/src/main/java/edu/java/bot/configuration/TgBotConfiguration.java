package edu.java.bot.configuration;

import edu.java.bot.controller.TelegramBot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TgBotConfiguration {
    @Bean
    public TelegramBot runBot(ApplicationConfig applicationConfig) {
        TelegramBot bot = new TelegramBot(applicationConfig.telegramToken());
        bot.run();
        return bot;
    }
}
