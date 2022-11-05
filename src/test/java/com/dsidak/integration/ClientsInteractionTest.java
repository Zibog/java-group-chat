package com.dsidak.integration;

import com.dsidak.client.Client;
import com.dsidak.server.Server;
import com.dsidak.utils.Message;
import com.dsidak.utils.Testable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Queue;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClientsInteractionTest implements Testable {
    private Server server;

    @BeforeEach
    public void init() {
        new Thread(this::startServer).start();
    }

    @Test
    public void testOneClient() throws IOException, TimeoutException {
        Client client = new Client(new Socket("localhost", 7700), "client");
        startClient(client);

        Message message1 = Message.of("client", "test");
        client.sendMessage(message1);
        Queue<Message> messages = client.getMessages();
        Testable.waitFor(() -> messages.size() == 1);

        Message message2 = Message.of("client", "test");
        client.sendMessage(message2);
        Testable.waitFor(() -> messages.size() == 2);
        assertTrue(messages.contains(message1));
        assertTrue(messages.contains(message2));
    }

    @Test
    public void testTwoClients() throws IOException, TimeoutException {
        Client client1 = new Client(new Socket("localhost", 7700), "client1");
        startClient(client1);
        Client client2 = new Client(new Socket("localhost", 7700), "client2");
        startClient(client2);

        Message message1 = Message.of("client1", "test");
        client1.sendMessage(message1);
        Message message2 = Message.of("client2", "test");
        client2.sendMessage(message2);

        Queue<Message> messages1 = client1.getMessages();
        Testable.waitFor(() -> messages1.size() == 2);
        assertTrue(messages1.contains(message1));
        assertTrue(messages1.contains(message2));

        Queue<Message> messages2 = client2.getMessages();
        Testable.waitFor(() -> messages2.size() == 2);
        assertTrue(messages2.contains(message1));
        assertTrue(messages2.contains(message2));
    }

    @Test
    public void testThreeClients() throws IOException, TimeoutException {
        Client client1 = new Client(new Socket("localhost", 7700), "client1");
        startClient(client1);
        Client client2 = new Client(new Socket("localhost", 7700), "client2");
        startClient(client2);
        Client client3 = new Client(new Socket("localhost", 7700), "client3");
        startClient(client3);

        Message message1 = Message.of("client1", "test");
        client1.sendMessage(message1);
        Message message2 = Message.of("client2", "test");
        client2.sendMessage(message2);

        Queue<Message> messages1 = client1.getMessages();
        Testable.waitFor(() -> messages1.size() == 4);
        assertTrue(messages1.contains(message1));
        assertTrue(messages1.contains(message2));

        Queue<Message> messages2 = client2.getMessages();
        Testable.waitFor(() -> messages2.size() == 3);
        assertTrue(messages2.contains(message1));
        assertTrue(messages2.contains(message2));

        Queue<Message> messages3 = client3.getMessages();
        Testable.waitFor(() -> messages3.size() == 2);
        assertTrue(messages3.contains(message1));
        assertTrue(messages3.contains(message2));
    }

    @Test
    public void testNewConnection() throws IOException, TimeoutException {
        Client client1 = new Client(new Socket("localhost", 7700), "client1");
        startClient(client1);

        Queue<Message> messages1 = client1.getMessages();
        assertEquals(0, messages1.size());

        Message message1 = Message.of("client1", "test");
        client1.sendMessage(message1);
        Testable.waitFor(() -> messages1.size() == 1);
        assertTrue(messages1.contains(message1));

        Client client2 = new Client(new Socket("localhost", 7700), "client2");
        startClient(client2);

        Testable.waitFor(() -> messages1.size() == 2);

        Queue<Message> messages2 = client2.getMessages();
        assertEquals(0, messages2.size());

        Message message2 = Message.of("client2", "test");
        client2.sendMessage(message2);
        Testable.waitFor(() -> messages2.size() == 1);
        assertTrue(messages2.contains(message2));

        Testable.waitFor(() -> messages1.size() == 3);
        assertTrue(messages1.contains(message2));
    }

    private void startServer() {
        try {
            server = new Server(new ServerSocket(7700));
        } catch (IOException e) {
            e.printStackTrace();
        }
        server.start();
    }

    private void startClient(Client client) {
        new Thread(() -> {
            client.listenMessage();
            client.run();
        }).start();
    }
}
