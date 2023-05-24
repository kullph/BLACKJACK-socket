import java.io.*;
import java.net.*;
import java.util.*;

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
    
    static class ThreadForServerMultipleClient extends Thread {
        private Socket clientSocket;
        private int clientNumber;
        private static int playerNumberCounter = 1;
    
        public ThreadForServerMultipleClient(Socket clientSocket, int clientNumber) {
            this.clientSocket = clientSocket;
            this.clientNumber = clientNumber;
        }
    
        @Override
        public void run() {
            System.out.println("Client#" + clientNumber + " connected");
            
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                String inputLine;
    
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("Client#" + clientNumber + ": " + inputLine);
                    out.println("svrc: " + inputLine);
    
                    // CREATE 4 1234
                    if (inputLine.startsWith("CREATE")) {
                        String[] commandParts = inputLine.split(" ");
                        int roomPort = Integer.parseInt(commandParts[1]) * 10000 + Integer.parseInt(commandParts[2]);
                        int limit = Integer.parseInt(commandParts[1]);
                        System.out.println(clientNumber + " " + roomPort);
                        try {
                            ServerSocket room = new ServerSocket(roomPort, limit);
                            List<Socket> roomClientSockets = new ArrayList<>();
                            List<Integer> roomScores = new ArrayList<>();
                            out.println(this.clientNumber + " CREATED " + roomPort);
    
                            while (true) {
                                Socket player = room.accept();
                                int playerNumber = playerNumberCounter++;
                                if(playerNumber > limit){
                                    player.close();
                                    playerNumber = playerNumberCounter--;
                                }
                                else{
                                    Room RoomThread = new Room(player, playerNumber, roomClientSockets, roomScores);
                                    roomClientSockets.add(player);
                                    roomScores.add(-1);
                                    RoomThread.start();
                                }
                            }
                        } catch (IOException error) {
                            error.printStackTrace();
                        }
                    }
                }
            } catch (IOException error) {
                error.printStackTrace();
            }
        }
    }
    static class Room extends Thread {
        private Socket playerSocket;
        private int playerNumber;
        private int score;
        private List<Socket> roomClientSockets;
        private List<Integer> roomScores;
    
        public Room(Socket playerSocket, int playerNumber,List<Socket> roomClientSockets,List<Integer> roomScores) {
            this.playerSocket = playerSocket;
            this.playerNumber = playerNumber;
            this.score = 0;
            this.roomClientSockets = roomClientSockets;
            this.roomScores = roomScores;
        }
    
        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(playerSocket.getInputStream()));
                PrintWriter out = new PrintWriter(playerSocket.getOutputStream(), true);
                String inputLine;
                out.println(playerNumber + " join " + playerSocket.getLocalPort());
                while ((inputLine = in.readLine()) != null) {
                    out.println("rrc: " + inputLine);
                    System.out.println(playerNumber + ": " + inputLine);

                    // SCOREX 21 - boardcast
                    if(inputLine.equals("SCOREX 21")){
                        System.out.println("SOMEONE BJ!");
                        for (Socket clientSocket : roomClientSockets) {
                            try {
                                PrintWriter outBC = new PrintWriter(clientSocket.getOutputStream(), true);
                                if (clientSocket.equals(playerSocket)) {
                                    outBC.println("STATUS win");
                                } else {
                                    outBC.println("STATUS lose");
                                }
                        
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    if(inputLine.startsWith("SCORE")){
                        String[] commandParts = inputLine.split(" ");
                        this.score = Integer.parseInt(commandParts[1]);
                        roomScores.set(playerNumber-1,Integer.parseInt(commandParts[1]));  
                    }

                    if(inputLine.startsWith("STATUS")){
                        int max = -1;
                        int maxi = 0;
                        for(int i=0;i<roomClientSockets.size();i++){
                            if(roomScores.get(i) != null && roomScores.get(i) > max){
                                max = roomScores.get(i);
                                maxi = i;
                            }
                        }
                        for(int i=0;i<roomClientSockets.size();i++){
                            if(roomClientSockets.get(i) != null && roomClientSockets.get(i) == roomClientSockets.get(maxi)){
                                PrintWriter outBC = new PrintWriter(roomClientSockets.get(i).getOutputStream(), true);
                                outBC.println("STATUS win");
                            }
                            else if(roomClientSockets.get(i) != null && roomClientSockets.get(i) != roomClientSockets.get(maxi)){
                                PrintWriter outBC = new PrintWriter(roomClientSockets.get(i).getOutputStream(), true);
                                outBC.println("STATUS lose");
                            }
                        }
                    }
                }
            } catch (IOException error) {
                error.printStackTrace();
            }
        }
    }

}