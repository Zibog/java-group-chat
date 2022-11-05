package com.dsidak.utils;

import java.io.BufferedWriter;
import java.io.IOException;

public class MessageUtils {
    public static void sendMessage(Message message, BufferedWriter writer) throws IOException {
        sendMessage(message.toString(), writer);
    }

    public static void sendMessage(String message, BufferedWriter writer) throws IOException {
        writer.write(message);
        writer.newLine();
        writer.flush();
    }
}
