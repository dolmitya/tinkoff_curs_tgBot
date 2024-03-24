package edu.java.servises.interfaces;

public interface TgChatService {
    void register(long tgChatId, String username);

    void unregister(long tgChatId);

    void setState(long tgChatId, String state);

    String getState(long tgChatId);
}
