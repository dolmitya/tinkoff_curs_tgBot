package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import org.example.dto.response.ListLinksResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class List implements Command {
    private final ScrapperClient scrapperClient;

    public List(ScrapperClient scrapperClient) {
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
            ListLinksResponse links = scrapperClient.getLinks(idChat);
            StringBuilder resultLinks = new StringBuilder();
            if (links.size().equals(0)) {
                resultLinks.append("Отслеживаемых ссылок нет!");
            } else {
                resultLinks.append("Отслеживаемые ссылки:").append(System.lineSeparator());
                links.links()
                    .forEach(linkResponse -> resultLinks.append(linkResponse.url()).append(System.lineSeparator()));
            }

            return new SendMessage(idChat, resultLinks.toString());
        }
        return new SendMessage(idChat, "Вы не авторизованы!");
    }
}
