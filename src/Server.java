import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        int lobbyPort = 10000;
        int clientCount = 1;

        try {
            ServerSocket lobbySocket = new ServerSocket(lobbyPort);
            System.out.println("Lobby server is running and waiting for connections on port " + lobbyPort);

            while (true) {
                Socket clientSocket = lobbySocket.accept();
                // System.out.println("client: " + clientSocket.getInetAddress().getHostName());

                ThreadForServerMultipleClient clientHandler = new ThreadForServerMultipleClient(clientSocket, clientCount);
                clientHandler.start();
                clientCount++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ThreadForServerMultipleClient extends Thread {
    private Socket clientSocket;
    private int clientNumber;

    public ThreadForServerMultipleClient(Socket clientSocket, int clientNumber) {
        this.clientSocket = clientSocket;
        this.clientNumber = clientNumber;
    }

    @Override
    public void run() {
        System.out.println("Client#" + clientNumber + " connected");
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Client#" + clientNumber + ": " + inputLine);
                out.println("svrc: "+inputLine);

                //CREATE 4 1234
                if(inputLine.startsWith("CREATE")){
                    String[] commandParts = inputLine.split(" ");
                    int roomPort = Integer.parseInt(commandParts[1])*10000 + Integer.parseInt(commandParts[2]);
                    int limit = Integer.parseInt(commandParts[1]);
                    ServerSocket room = new ServerSocket(roomPort, limit);
                    while (true) {
                                Socket player = room.accept();
                                // System.out.println("client: " + clientSocket.getInetAddress().getHostName());
                
                                Room RoomThread = new Room(player, clientNumber);
                                RoomThread.start();
                            }
                }
            }

        }
        catch(IOException error){
            error.printStackTrace();
        }
    }
}

class Room extends Thread{
    private Socket playerSocket;
    private int playerNumber;

    public Room(Socket playerSocket,int playerNumber){
        this.playerSocket = playerSocket;
        this.playerNumber = playerNumber;
    }

    @Override
    public void run() {
        
    }
}
