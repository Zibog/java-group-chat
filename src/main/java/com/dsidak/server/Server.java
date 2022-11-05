package com.dsidak.server;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@SuppressWarnings("ClassCanBeRecord")
public class Server {
    /**
     * The ServerSocket for accepting new connections
     */
    private final ServerSocket serverSocket;

    public Server(@NotNull ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void start() {
        try {
            System.out.println("Server started at " + serverSocket.getLocalPort());
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("A new user has connected!");
                ClientHandler handler = new ClientHandler(socket);

                // TODO: migrate to executor
                Thread thread = new Thread(handler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public int getLocalPort() {
        return serverSocket.getLocalPort();
    }

    public void close() {
        try {
            System.out.println("Server shutdown");
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
