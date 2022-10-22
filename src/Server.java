import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public record Server(ServerSocket serverSocket) {
    public void start() {
        try {
            System.out.println("Server started");
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

    public void close() {
        try {
            System.out.println("Server shutdown");
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
