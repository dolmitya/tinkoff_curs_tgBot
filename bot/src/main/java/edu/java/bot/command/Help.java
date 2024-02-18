package edu.java.bot.command;

import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.users.Users;

public class Help implements Command {
    @Override
    public SendMessage apply(Update update, Users users) {
        return new SendMessage(
            update.message().chat().id(),
            "/help -- вывести окно с командами\n"
                + "/start -- зарегистрировать пользователя\n"
                + "/track -- начать отслеживание ссылки\n"
                + "/untrack -- прекратить отслеживание ссылки\n"
                + "/list -- показать список отслеживаемых ссылок "
        );
    }
}
