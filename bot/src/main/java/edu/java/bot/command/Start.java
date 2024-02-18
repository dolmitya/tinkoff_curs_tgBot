package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.users.User;
import edu.java.bot.users.Users;

public class Start implements Command {

    @Override
    public SendMessage apply(Update update, Users users) {
        User user = new User(update.message().chat().username(), update.message().chat().id());
        if (users.find(user.getId())) {
            return new SendMessage(update.message().chat().id(), "Вы уже зарегистрированы");
        } else {
            users.usersMap.put(user.getId(), user);
            return new SendMessage(update.message().chat().id(), "Добро пожаловать");
        }

    }
}
