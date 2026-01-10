import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CentralServer {

    private List<Client> clients = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        CentralServer server = new CentralServer();
        server.startServer();
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(9999)) {
            while (true) {
                try {
                    Socket clientSocket = serverSocket.accept();
                    ClientHandler clientHandler = new ClientHandler(clientSocket, clients);
                    Thread thread = new Thread(clientHandler);
                    thread.start();
                } catch (IOException e) {
                    System.err.println("Accept failed.");
                }
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port: 9999.");
            System.exit(1);
        }
    }
}