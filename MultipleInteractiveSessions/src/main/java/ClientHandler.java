import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private List<ClientHandler> clients;

    public ClientHandler(Socket clientSocket, List<ClientHandler> clients) throws IOException {
        this.client = clientSocket;
        this.clients = clients;

        this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.out = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String request = in.readLine();
                if (request.contains("name")) {
                    out.println("my name is Kalim");
                } else if (request.startsWith("say")) {
                    int firstSpace = request.indexOf(" ");
                    outToAll(request.substring(firstSpace+1));
                } else {
                    out.println("type 'tell me a name' to get a name");
                }
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            out.close();
        }
    }

    private void outToAll(String substring) {
        for (ClientHandler aClient : clients) {
            aClient.out.println(substring);
        }
    }
}
