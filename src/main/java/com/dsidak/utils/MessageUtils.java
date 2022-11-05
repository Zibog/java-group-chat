package com.dsidak.utils;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.IOException;

public class MessageUtils {
    public static void sendMessage(@NotNull Message message, @NotNull BufferedWriter writer) throws IOException {
        sendMessage(message.toString(), writer);
    }

    public static void sendMessage(@NotNull String message, @NotNull BufferedWriter writer) throws IOException {
        writer.write(message);
        writer.newLine();
        writer.flush();
    }
}
