package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.users.State;
import edu.java.bot.users.User;
import edu.java.bot.users.Users;

public class Track implements Command {
    @Override
    public SendMessage apply(Update update, Users users) {
        User user = new User(update.message().chat().username(), update.message().chat().id());
        if (users.find(user.getId())) {
            users.usersMap.get(user.getId()).state = State.ADD_LINK;
            return new SendMessage(update.message().chat().id(), "Вставьте ссылку на источник(/cancel для отмены)");
        } else {
            return new SendMessage(update.message().chat().id(), "Вы не зарегистрированы");
        }
    }
}
