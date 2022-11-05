package com.dsidak.client;

import com.dsidak.utils.Message;
import com.dsidak.utils.MessageUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

public class Client {
    public static final int MAX_CAPACITY = 50;

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private Queue<Message> messages;
    private final String username;

    public Client(Socket socket, String clientName) {
        try {
            this.socket = socket;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            messages = new LinkedBlockingQueue<>(MAX_CAPACITY);
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
        this.username = clientName;
    }

    public void run() {
        try {
            MessageUtils.sendMessage(username, writer);

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String messageToSend = scanner.nextLine();
                Message message = Message.of(username, messageToSend);
                sendMessage(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    public void listenMessage() {
        new Thread(() -> {
            String messageFromChat;
            while (socket.isConnected()) {
                try {
                    messageFromChat = reader.readLine();
                    if (!messageFromChat.matches(".*:.*")) {
                        readMessage(messageFromChat);
                    } else {
                        readMessage(Message.of(messageFromChat));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    close();
                    // TODO: instead of break wait for connection restored
                    break;
                }
            }
        }).start();
    }

    public void sendMessage(Message message) throws IOException {
        MessageUtils.sendMessage(message, writer);
        messages.add(message);
    }

    public void readMessage(Message message) {
        System.out.println(message.toString());
        messages.add(message);
    }

    private void readMessage(String message) {
        System.out.println(message);
        messages.add(Message.of(message, ""));
    }

    public BufferedWriter getWriter() {
        return writer;
    }

    public Queue<Message> getMessages() {
        return messages;
    }

    public void close() {
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
