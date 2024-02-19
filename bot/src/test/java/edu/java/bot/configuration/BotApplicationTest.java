package edu.java.bot.configuration;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.Command;
import edu.java.bot.command.Start;
import edu.java.bot.controller.CommandHandler;
import edu.java.bot.users.User;
import edu.java.bot.users.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BotApplicationTest {

    @Test
    @DisplayName("Test /start command")
    void testHandleStartCommand() {
        Users users = new Users();
        Command startCommand = new Start();
        Update update = mock(Update.class);
        Message message = mock(Message.class);
        when(update.message()).thenReturn(message);
        when(message.text()).thenReturn("/start");
        when(update.message().chat()).thenReturn(new Chat());
        SendMessage response = startCommand.apply(update, users);

        assertThat("Добро пожаловать").isEqualTo(response.getParameters().get("text"));
    }

    @Test
    @DisplayName("Test Command Handler Without Autentification")
    void testWhithoutAut() {
        Users users = new Users();
        CommandHandler commandHand = new CommandHandler();
        Update update = mock(Update.class);
        Chat chat = mock(Chat.class);
        Message message = mock(Message.class);
        when(update.message()).thenReturn(message);
        when(update.message().chat()).thenReturn(chat);
        when(update.message().chat().id()).thenReturn(12L);
        when(message.text()).thenReturn("/list").thenReturn("/track");
        SendMessage response = commandHand.handle(update, users);

        assertThat("Вы не зарегистрированы").isEqualTo(response.getParameters().get("text"));
        assertThat("Вы не зарегистрированы").isEqualTo(response.getParameters().get("text"));
    }

    @Test
    @DisplayName("Test /help")
    void testHelp() {
        Users users = new Users();
        CommandHandler commandHand = new CommandHandler();
        Update update = mock(Update.class);
        Chat chat = mock(Chat.class);
        Message message = mock(Message.class);
        when(update.message()).thenReturn(message);
        when(update.message().chat()).thenReturn(chat);
        when(update.message().chat().id()).thenReturn(12L);
        when(message.text()).thenReturn("/help");
        SendMessage response = commandHand.handle(update, users);

        assertThat("/help -- вывести окно с командами\n"
            + "/start -- зарегистрировать пользователя\n"
            + "/track -- начать отслеживание ссылки\n"
            + "/untrack -- прекратить отслеживание ссылки\n"
            + "/list -- показать список отслеживаемых ссылок ").isEqualTo(response.getParameters().get("text"));
    }

    @Test
    @DisplayName("Test user base")
    void testUserBase() {
        Users users = new Users();
        Update update = mock(Update.class);
        CommandHandler commandHand = new CommandHandler();
        Chat chat = mock(Chat.class);
        Message message = mock(Message.class);

        when(update.message()).thenReturn(message);
        when(update.message().chat()).thenReturn(chat);
        when(update.message().chat().id()).thenReturn(12L);
        when(message.text()).thenReturn("/start");
        SendMessage response = commandHand.handle(update, users);
        assertThat(response.getParameters().get("text")).isEqualTo("Добро пожаловать");

        when(message.text()).thenReturn("/track");
        response = commandHand.handle(update, users);
        assertThat(response.getParameters().get("text"))
            .isEqualTo("Вставьте ссылку на источник(/cancel для отмены)");

        when(message.text()).thenReturn("https://github.com/dolmitya/tinkoff_curs_tgBot/pulls");
        response = commandHand.handle(update, users);
        assertThat(response.getParameters().get("text"))
            .isEqualTo("Ссылка добавлена для отсживания");

        when(message.text()).thenReturn("/track");
        response = commandHand.handle(update, users);
        assertThat(response.getParameters().get("text"))
            .isEqualTo("Вставьте ссылку на источник(/cancel для отмены)");

        when(message.text()).thenReturn("https://stackoverflow.com/questions/6038061"
            + "/regular-expression-to-find-urls-within-a-string%20");
        response = commandHand.handle(update, users);
        assertThat(response.getParameters().get("text"))
            .isEqualTo("Ссылка добавлена для отсживания");

        when(message.text()).thenReturn("/list");
        response = commandHand.handle(update, users);
        assertThat(response.getParameters().get("text"))
            .isEqualTo("""
                https://github.com/dolmitya/tinkoff_curs_tgBot/pulls
                --------------------------------------------------------------------------------------------------
                https://stackoverflow.com/questions/6038061/regular-expression-to-find-urls-within-a-string%20""");
    }
}
