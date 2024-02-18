package edu.java.bot.configuration;

import com.pengrad.telegrambot.model.Chat;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import edu.java.bot.command.Command;
import edu.java.bot.command.Start;
import edu.java.bot.users.Users;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}
