import java.net.*;
import java.io.*;
import java.util.List;

public class ClientHandler implements Runnable {

    private Socket socket;
    private List<Client> clients;

    public ClientHandler(Socket socket, List<Client> clients) {
        this.socket = socket;
        this.clients = clients;
    }

    @Override
    public void run() {
        try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            GUIProtocol gp = new GUIProtocol(clients);
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equals("FINISH")) {
                    break;
                }
                String outputLine = gp.processInput(inputLine);
                out.println(outputLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}