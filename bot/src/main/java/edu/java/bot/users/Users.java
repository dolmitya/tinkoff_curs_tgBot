package edu.java.bot.users;

import java.util.HashMap;
import java.util.Map;

public class Users {
    public Map<Long, User> usersMap = new HashMap<>();

    public boolean find(Long id) {
        return usersMap.containsKey(id);
    }
}
