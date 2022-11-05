package com.dsidak.utils;

public class Message {
    private final String username;
    private final String content;

    private Message(String username, String content) {
        this.username = username;
        this.content = content;
    }

    public static Message of(String message) {
        if (!message.matches(".*:.*")) {
            //throw new IllegalArgumentException("Message without username: " + message);
            System.out.println("Message without username: " + message);
            return new Message("", message);
        }

        String[] messageData = message.split(":", 2);
        String username = messageData[0];
        if (username.startsWith("[") && username.endsWith("]")) {
            username = username.substring(1, username.length() - 1);
        }

        return new Message(username, messageData[1].trim());
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

        return username.equals(message.username) && content.equals(message.content);
    }

    @Override
    public String toString() {
        return "[" + username + "]: " + content;
    }
}
