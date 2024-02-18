package edu.java.bot.users;

import java.util.HashSet;
import java.util.Set;

public class User {
    private final String name;
    private final Long id;
    private Set<String> urls;
    public State state;

    public User(String name, Long id) {
        this.name = name;
        this.id = id;
        urls = new HashSet<>();
        state = State.NONE;
    }

    public boolean addUrl(String url) {
        if (!urls.contains(url)) {
            urls.add(url);
            return true;
        }
        return false;
    }

    public boolean removeUrl(String url) {
        if (urls.contains(url)) {
            urls.remove(url);
            return true;
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public String urlstoString() {
        if (!urls.isEmpty()) {
            return String.join(
                "\n-------------------------------------------------"
                    + "-------------------------------------------------\n",
                urls
            );
        } else {
            return ("Ваш список отслеживаеммых ссылок пуст");
        }
    }

}
