package edu.java.bot.check;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CheckerUrlTest {

    @Test
    @DisplayName("Test github")
    void checkGithub() {
        String url = "https://github.com/dolmitya/tinkoff_curs_tgBot/pulls";
        assertTrue(CheckerUrl.check(url));
    }

    @Test
    @DisplayName("Test stackoverflow")
    void checkStackoverflow() {
        String url = "https://stackoverflow.com/questions/6038061/regular-expression-to-find-urls-within-a-string%20";
        assertTrue(CheckerUrl.check(url));
    }

    @Test
    @DisplayName("Test tinkoff")
    void checkTink() {
        String url = "https://edu.tinkoff.ru/my-activities/courses/stream/b37f2c9a-b73c-4cc8-a092-0bcbf49faa"
            + "c7/unit/779550c4-1f2b-42b8-b8b9-e51f46ee4220";
        assertFalse(CheckerUrl.check(url));
    }
}
