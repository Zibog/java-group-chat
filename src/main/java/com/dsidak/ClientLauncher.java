package com.dsidak;

import com.dsidak.client.Client;

import java.io.IOException;
import java.net.Socket;

public class ClientLauncher {
    public static void main(String[] args) throws IOException {
        // TODO: use properties
        Socket socket = new Socket("localhost", 5500);
        Client client = new Client(socket, args[0]);
        client.listenMessage();
        client.run();
    }
}
