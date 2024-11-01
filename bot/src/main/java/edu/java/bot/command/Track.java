package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import org.springframework.stereotype.Component;

@Component
public class Track implements Command {
    private final ScrapperClient scrapperClient;

    public Track(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    @Override
    public SendMessage apply(Update update) {
        String username = update.message().chat().username();
        long idChat = update.message().chat().id();
        if (scrapperClient.isRegister(idChat)) {
            scrapperClient.setState(idChat, "ADD");
            return new SendMessage(idChat, "Вставьте ссылку на источник(/cancel для отмены)");
        } else {
            return new SendMessage(idChat, "Вы не зарегистрированы!");
        }
    }
}
