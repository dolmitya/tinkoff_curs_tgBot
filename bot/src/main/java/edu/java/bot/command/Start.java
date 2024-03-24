package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Start implements Command {
    private final ScrapperClient scrapperClient;

    public Start(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    @Override
    public SendMessage apply(Update update) {
        String username = update.message().chat().username();
        long idChat = update.message().chat().id();
        try {
            scrapperClient.createChat(idChat, username);
            scrapperClient.deleteChat(idChat);
        } catch (Exception e) {
            return new SendMessage(idChat, "Вы уже зарегестрированы!");
        }
        scrapperClient.createChat(idChat, username);
        return new SendMessage(idChat, "Добро пожаловать!");
    }
}
