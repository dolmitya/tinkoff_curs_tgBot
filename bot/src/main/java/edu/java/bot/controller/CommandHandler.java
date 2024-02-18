package edu.java.bot.controller;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.check.CheckerUrl;
import edu.java.bot.command.Command;
import edu.java.bot.command.Help;
import edu.java.bot.command.List;
import edu.java.bot.command.Start;
import edu.java.bot.command.Track;
import edu.java.bot.command.Untrack;
import edu.java.bot.users.State;
import edu.java.bot.users.Users;
import java.util.Map;

@SuppressWarnings("MultipleStringLiterals")
public class CommandHandler {

    private final Map<String, Command> commands;

    public CommandHandler() {
        this.commands = Map.of(
            "/start", new Start(),
            "/help", new Help(),
            "/list", new List(),
            "/track", new Track(),
            "/untrack", new Untrack()
        );
    }

    public SendMessage handle(Update update, Users users) {
        Long id = update.message().chat().id();
        String message = "Введите правильную ссылку(/cancel для отмены)";
        if (users.find(id) && users.usersMap.get(id).state.equals(State.ADD_LINK)) {
            String url = update.message().text();
            if (CheckerUrl.check(url)) {
                users.usersMap.get(id).state = State.NONE;
                if (users.usersMap.get(id).addUrl(url)) {
                    message = "Ссылка добавлена для отсживания";
                } else {
                    message = "Такая ссылка уже существует";
                }
            } else if (url.equals("/cancel")) {
                users.usersMap.get(id).state = State.NONE;
                message = "Вы отменили ввод ссылки";
            }
            return new SendMessage(id, message);
        }
        if (users.find(id) && users.usersMap.get(id).state.equals(State.DEL_LINK)) {
            String url = update.message().text();
            if (CheckerUrl.check(url)) {
                users.usersMap.get(id).state = State.NONE;
                if (users.usersMap.get(id).removeUrl(url)) {
                    message = "Ссылка больше не отслеживается";
                } else {
                    message = "Такой ссылки у вас нет в отслеживаемых";
                }
            } else if (url.equals("/cancel")) {
                users.usersMap.get(id).state = State.NONE;
                message = "Вы отменили ввод ссылки";
            }
            return new SendMessage(id, message);
        }
        message = update.message().text();
        Command command = commands.get(message);
        if (command != null) {
            return command.apply(update, users);
        }
        return new SendMessage(id, "Такой команды не существует!");
    }
}
