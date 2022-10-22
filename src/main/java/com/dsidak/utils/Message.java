package com.dsidak.utils;

@SuppressWarnings("ClassCanBeRecord")
public class Message {
    private final String username;
    private final String content;

    private Message(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public static Message of(String messageWithUsername) {
        if (messageWithUsername.matches(".*:.*")) {
            String[] messageData = messageWithUsername.split(":", 2);
            return new Message(messageData[0], messageData[1].trim());
        }
        // TODO: handle incorrect message
        return new Message("", messageWithUsername);
    }

    public static Message of(String username, String content) {
        return new Message(username, content);
    }

    public String getUsername() {
        return username;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return username + ": " + content;
    }
}
