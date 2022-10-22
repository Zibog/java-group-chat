package com.dsidak.server;

import com.dsidak.utils.Message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {
    public static List<ClientHandler> clientHandlers = new ArrayList<>();

    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private String username;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            username = reader.readLine();
            clientHandlers.add(this);
            broadcastMessage(Message.of("SERVER", username + " has entered the chat!"));
        } catch (IOException e) {
            e.printStackTrace();
            close();
        }
    }

    @Override
    public void run() {
        String messageFromClient;

        while (socket.isConnected()) {
            try {
                messageFromClient = reader.readLine();
                broadcastMessage(Message.of(messageFromClient));
            } catch (IOException e) {
                close();
                break;
            }
        }
    }

    public void broadcastMessage(Message messageToSend) throws IOException {
        for (ClientHandler handler : clientHandlers) {
            if (!handler.username.equals(username)) {
                handler.writer.write(messageToSend.toString());
                handler.writer.newLine();
                handler.writer.flush();
            }
        }
    }

    public void removeHandler() throws IOException {
        boolean removed = clientHandlers.remove(this);
        if (removed) {
            broadcastMessage(Message.of("SERVER", username + " has left the chat!"));
        }
    }

    public void close() {
        try {
            removeHandler();
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
