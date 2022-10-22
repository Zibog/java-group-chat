import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ClientLauncher {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        // TODO: use properties
        Socket socket = new Socket("localhost", 5500);
        Client client = new Client(socket, username);
        client.listenMessage();
        client.sendMessage();
    }
}
