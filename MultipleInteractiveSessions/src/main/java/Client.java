import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 4444;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT)) {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

            ServerConnection serverConnection = new ServerConnection(socket);

            new Thread(serverConnection).start();

            while (true) {
                System.out.println("> ");
                String command = keyboard.readLine();

                if (command.equals("quit")) break;

                out.println(command);

            }

            socket.close();
            System.exit(0);

        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
