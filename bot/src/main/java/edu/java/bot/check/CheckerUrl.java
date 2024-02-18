package edu.java.bot.check;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import lombok.Data;

@Data
public class CheckerUrl {

    private CheckerUrl() {
    }

    private static final List<String> TRUEURLS = List.of(
        "https://stackoverflow.com/",
        "https://github.com/"
    );

    private static boolean checkUrls(String link) {
        return !TRUEURLS.stream()
            .filter(link::startsWith)
            .toList()
            .isEmpty();
    }

    public static boolean check(String link) {
        try {
            URL isLink = new URL(link);
            return checkUrls(link);
        } catch (MalformedURLException e) {
            return false;
        }
    }
}
