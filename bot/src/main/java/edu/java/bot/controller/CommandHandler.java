package edu.java.bot.controller;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.client.ScrapperClient;
import edu.java.bot.command.Command;
import edu.java.bot.command.Help;
import edu.java.bot.command.List;
import edu.java.bot.command.Start;
import edu.java.bot.command.Track;
import edu.java.bot.command.Untrack;
import edu.java.bot.commands.AddDelLInk;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
@SuppressWarnings("MultipleStringLiterals")
public class CommandHandler {

    private ScrapperClient scrapperClient;
    private final Map<String, Command> commands;

    public CommandHandler(ScrapperClient scrapperClient) {
        this.scrapperClient = scrapperClient;
        this.commands = Map.of(
            "/start", new Start(scrapperClient),
            "/help", new Help(),
            "/list", new List(scrapperClient),
            "/track", new Track(scrapperClient),
            "/untrack", new Untrack(scrapperClient)
        );
    }

    public SendMessage handle(Update update) {
        if (update.message().text() != null) {
            Long id = update.message().chat().id();
            String username = update.message().chat().username();
            try {
                scrapperClient.createChat(id, username);
                scrapperClient.deleteChat(id);
            } catch (Exception e) {
                if (scrapperClient.getState(id).state().equals("ADD")) {
                    return new AddDelLInk(scrapperClient).addLink(update, id);
                }
                if (scrapperClient.getState(id).state().equals("DEL")) {
                    return new AddDelLInk(scrapperClient).delLink(update, id);
                }
            }
            String message = update.message().text();
            Command command = commands.get(message);
            if (command != null) {
                return command.apply(update);
            }
        }
        return new SendMessage(update.message().chat().id(), "Такой команды не существует!");
    }
}
