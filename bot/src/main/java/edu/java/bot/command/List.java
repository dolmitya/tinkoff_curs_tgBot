package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.users.User;
import edu.java.bot.users.Users;

public class List implements Command {
    @Override
    public SendMessage apply(Update update, Users users) {
        User user = new User(update.message().chat().username(), update.message().chat().id());
        if (users.contains(user.getId())) {
            return new SendMessage(update.message().chat().id(), users.usersMap.get(user.getId()).urlstoString());
        } else {
            return new SendMessage(update.message().chat().id(), "Вы не зарегистрированы");
        }
    }
}
