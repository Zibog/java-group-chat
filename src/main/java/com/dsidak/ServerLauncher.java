package com.dsidak;

import com.dsidak.server.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerLauncher {
    public static void main(String[] args) throws IOException {
        // TODO: use properties
        ServerSocket serverSocket = new ServerSocket(5500);
        Server server = new Server(serverSocket);
        server.start();
    }
}
