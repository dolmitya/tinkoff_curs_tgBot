package edu.java.bot.commands;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import java.net.URI;
import java.net.URISyntaxException;
import org.example.dto.LinkParser;
import org.example.dto.request.AddLinkRequest;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("MultipleStringLiterals")
public class AddDelLInk {
    private final ScrapperClient scrapperClient;

    public AddDelLInk(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
    }

    public SendMessage addLink(Update update, Long id) {
        String message = "Такая ссылка уже существует!";
        String url = update.message().text();
        if (LinkParser.check(url)) {
            if (scrapperClient.getLinks(id).links().stream()
                .noneMatch(link -> link.url().toString().equals(url))) {
                message = "Ссылка добавлена для отсживания";
                try {
                    scrapperClient.setLink(id, new AddLinkRequest(new URI(url)));
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
            scrapperClient.setState(id, "NONE");
            return new SendMessage(id, message);
        } else if (url.equals("/cancel")) {
            scrapperClient.setState(id, "NONE");
            message = "Вы отменили ввод ссылки";
            return new SendMessage(id, message);
        }
        message = "Введите правильную ссылку(/cancel для отмены)";
        return new SendMessage(id, message);
    }

    public SendMessage delLink(Update update, Long id) {
        String message = "Такой ссылки у вас нет в отслеживаемых!";
        String url = update.message().text();
        if (LinkParser.check(url)) {
            if (scrapperClient.getLinks(id).links().stream()
                .anyMatch(link -> link.url().toString().equals(url))) {
                try {
                    scrapperClient.deleteLink(id, new AddLinkRequest(new URI(url)));
                    message = "Ссылка удалена!";
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
            scrapperClient.setState(id, "NONE");
            return new SendMessage(id, message);
        } else if (url.equals("/cancel")) {
            scrapperClient.setState(id, "NONE");
            message = "Вы отменили ввод ссылки";
            return new SendMessage(id, message);
        }
        message = "Введите правильную ссылку(/cancel для отмены)";
        return new SendMessage(id, message);
    }
}
