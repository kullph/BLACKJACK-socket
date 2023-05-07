import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(10000);
        System.out.println("Server is running and waiting for a connection...");

        Socket clientSocket = serverSocket.accept();
        System.out.println("Connection established with client " + clientSocket.getInetAddress().getHostName());

        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Client says: " + inputLine);
            out.println("Server received: " + inputLine);
        }

        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}