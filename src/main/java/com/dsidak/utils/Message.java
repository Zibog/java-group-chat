package com.dsidak.utils;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Message {
    private final String username;
    private final String content;

    private Message(@NotNull String username, @Nullable String content) {
        this.username = username;
        this.content = content;
    }

    @NotNull
    public static Message of(@NotNull String message) {
        if (!message.matches(".*:.*")) {
            throw new IllegalArgumentException("Message without username: " + message);
        }

        String[] messageData = message.split(":", 2);
        String username = messageData[0];
        if (username.startsWith("[") && username.endsWith("]")) {
            username = username.substring(1, username.length() - 1);
        }

        return new Message(username, messageData[1].trim());
    }

    @NotNull
    public static Message of(@NotNull String username, @Nullable String content) {
        return new Message(username, content);
    }

    @NotNull
    public String getUsername() {
        return username;
    }

    @Nullable
    public String getContent() {
        return content;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Message message)) {
            return false;
        }

        return username.equals(message.username) && Objects.equals(content, message.content);
    }

    @Override
    public String toString() {
        return "[" + username + "]: " + content;
    }
}
