import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) throws IOException {
        String host = "localhost";
        int lobby = 10000;

        Socket socket = new Socket(host, lobby);
        System.out.println("Connected to server " + host + ":" + lobby);

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        while (true) {
            userInput = stdIn.readLine();
            if (userInput.equals("exit")) {
                break;
            }
            out.println(userInput);
            System.out.println(in.readLine());
        }

        in.close();
        out.close();
        stdIn.close();
        socket.close();
    }
}
