import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class Client extends JFrame {
        
    public static void main(String[] args) throws IOException {

        Random random = new Random();
        String host = "localhost";
        int lobby = 10000;

        

        Socket lobbySocket = new Socket(host, lobby);
        System.out.println("Connected to " + host + ":" + lobby);

        BufferedReader in = new BufferedReader(new InputStreamReader(lobbySocket.getInputStream()));
        PrintWriter out = new PrintWriter(lobbySocket.getOutputStream(), true);

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        while (true) {
            userInput = stdIn.readLine();
            if (userInput.equals("exit")) {
                break;
            }
            //JOIN 41234
            else if(userInput.startsWith("JOIN")){
                int score = 0;
                System.out.println("joincmd");
                String[] commandParts = userInput.split(" ");
                int port = Integer.parseInt(commandParts[1]);
                Socket game = new Socket(host, port);
                BufferedReader gameIn = new BufferedReader(new InputStreamReader(game.getInputStream()));
                PrintWriter gameOut = new PrintWriter(game.getOutputStream(), true);

                BufferedReader gameStdIn = new BufferedReader(new InputStreamReader(System.in));
                String gameUserInput;
                //CALL GUI
                while (true) {

                    gameUserInput = gameStdIn.readLine();
                    if (gameUserInput.equals("exit")) {
                        break;
                    }
                    else if(gameUserInput.equals("start")){
                        score += random.nextInt(11) + 1;
                        score += random.nextInt(11) + 1;
                        System.out.println(score);
                        if(score == 21){
                            gameOut.println("SCOREX 21");
                        }
                        else if(score > 21){
                            gameOut.println("SCORE 0");
                        }
                    }
                    else if(gameUserInput.equals("more")){
                        score += random.nextInt(11) + 1;
                        System.out.println(score);
                    }
                    else if(gameUserInput.equals("fold")){
                        if(score > 21){
                            gameOut.println("SCORE 0");    
                        }
                        else{
                            gameOut.println("SCORE " + score);
                        }
                        
                    }

                    gameOut.println(gameUserInput);
                    System.out.println(gameIn.readLine());
                }
                
            }
            out.println(userInput);
            
            System.out.println(in.readLine());
            
        }

        in.close();
        out.close();
        stdIn.close();
        lobbySocket.close();
    }
}



// import java.io.*;
// import java.net.*;
// import java.util.Random;
// import javax.swing.*;
// import java.awt.*;
// import java.awt.event.ActionEvent;
// import java.awt.event.ActionListener;

// public class Client extends JFrame implements ActionListener{
//     private static PrintWriter out;
//     private static BufferedReader in;
//     private static PrintWriter Rout;
//     private static BufferedReader Rin;
//     private static String host = "localhost";
//     private static int lobby = 10000;
//     private Socket game;
//     private static Socket lobbySocket;
//     private int score = 0;
//     private static boolean x = false;
//     private int GameRoom;
//     private static int port;
    

//     //Room
//     private String gameStatus = "Status Playing";
//     private JPanel lobbyPanelRoom;
//     private JButton Draw;
//     private JButton Fold;
//     private JButton Start;
//     private JButton Ghost;
//     private JLabel TittleStatus;
//     private JLabel StatusLabel;
//     private JLabel TittlePoint;
//     private JLabel TotalPoint;

//     //Lobby
//     private JPanel lobbyPanelLobby;
//     private JButton join;
//     private JButton create;
//     private JTextField inputPlayer;
//     private JLabel TextPlayer;
//     private JLabel TextRoom;
//     private JTextField inputRoom;
    

//     private void LobbyGUI() {
       
//         setTitle("LOBBY");
//         setSize(500, 300);
//         setLocationRelativeTo(null);
//         setResizable(false);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//         lobbyPanelLobby = new JPanel(new GridLayout(3, 2));

//         inputPlayer = new JTextField(10);
//         inputRoom = new JTextField(10);
//         join = new JButton("Join");
//         create = new JButton("Create");
//         TextPlayer = new JLabel(" Enter number of player : ");
//         TextRoom = new JLabel(" Enter your NumberRoom : ");

//         lobbyPanelLobby.add(TextPlayer);
//         lobbyPanelLobby.add(inputPlayer);
//         lobbyPanelLobby.add(TextRoom);
//         lobbyPanelLobby.add(inputRoom);
//         lobbyPanelLobby.add(join);
//         lobbyPanelLobby.add(create);

//         add(lobbyPanelLobby, BorderLayout.CENTER);

//         join.addActionListener(this);
//         create.addActionListener(this);

//         setVisible(true); 
//     }

//     private void RoomGUI() {
//         setTitle("Room");
//         setSize(500, 300);
//         setLocationRelativeTo(null);
//         setResizable(false);
//         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//         lobbyPanelRoom = new JPanel(new GridLayout(4, 3));

//         Draw = new JButton("More card noi Ja Baby Girl!");
//         Fold = new JButton("No more card laewJa let's fight!");
//         Ghost = new JButton("");
//         Start = new JButton("Start laoJa");
//         TittleStatus = new JLabel(" Status : ");
//         StatusLabel = new JLabel(gameStatus);
//         TittlePoint = new JLabel(" Total Point : ");
//         TotalPoint = new JLabel(String.valueOf(score));
   

//         lobbyPanelRoom.add(TittleStatus);
//         lobbyPanelRoom.add(StatusLabel);
//         lobbyPanelRoom.add(TittlePoint);
//         lobbyPanelRoom.add(TotalPoint);
//         lobbyPanelRoom.add(Start);
//         lobbyPanelRoom.add(Draw);
//         lobbyPanelRoom.add(Ghost);
//         lobbyPanelRoom.add(Fold);

//         add(lobbyPanelRoom, BorderLayout.CENTER);

//         Start.addActionListener(this);
//         Draw.addActionListener(this);
//         Fold.addActionListener(this);
//         setVisible(true);
//     }

//     public static void main(String[] args) throws IOException {

//                 Random random = new Random();
//                 String host = "localhost";
//                 int lobby = 10000;
        
                
        
//                 Socket lobbySocket = new Socket(host, lobby);
//                 System.out.println("Connected to " + host + ":" + lobby);
        
//                 BufferedReader in = new BufferedReader(new InputStreamReader(lobbySocket.getInputStream()));
//                 PrintWriter out = new PrintWriter(lobbySocket.getOutputStream(), true);
        
//                 BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
//                 String userInput;
        
//                 while (x) {
//                     userInput = stdIn.readLine();
//                     if (userInput.equals("exit")) {
//                         break;
//                     }
//                     //JOIN 41234
//                     else if(userInput.startsWith("JOIN")){
//                         int score = 0;
//                         System.out.println("joincmd");
//                         String[] commandParts = userInput.split(" ");
//                         port = Integer.parseInt(commandParts[1]);
//                         Socket game = new Socket(host, port);
//                         BufferedReader gameIn = new BufferedReader(new InputStreamReader(game.getInputStream()));
//                         PrintWriter gameOut = new PrintWriter(game.getOutputStream(), true);
        
//                         BufferedReader gameStdIn = new BufferedReader(new InputStreamReader(System.in));
//                         String gameUserInput;
//                         //CALL GUI
//                         while (true) {
        
//                             gameUserInput = gameStdIn.readLine();
//                             if (gameUserInput.equals("exit")) {
//                                 break;
//                             }
//                             else if(gameUserInput.equals("start")){
//                                 score += random.nextInt(11) + 1;
//                                 score += random.nextInt(11) + 1;
//                                 if(score == 21){
//                                     gameOut.println("SCORE 21");
//                                 }
//                                 else if(score > 21){
//                                     gameOut.println("SCORE 0");
//                                 }
//                             }
//                             else if(gameUserInput.equals("more")){
//                                 score += random.nextInt(11) + 1;
//                             }
        
//                             gameOut.println(gameUserInput);
//                             System.out.println(gameIn.readLine());
//                         }
                        
//                     }
//                     out.println(userInput);
                    
//                     System.out.println(in.readLine());
                    
//                 }
        
//                 in.close();
//                 out.close();
//                 stdIn.close();
//                 lobbySocket.close();
//             }
        

//     public void actionPerformed(ActionEvent e) {
//         Random random = new Random();
//         String playerCount = inputPlayer.getText();
//         String roomNumber = inputRoom.getText();

//         if (e.getSource() == join) {
            
//             GameRoom = Integer.parseInt(playerCount)*10000 + Integer.parseInt(roomNumber);
//             System.out.println("GGame : " + GameRoom);
//             try {
//                 Socket game = new Socket(host, GameRoom);
//                 System.out.println("Connected to " + host + ":" + GameRoom);

//                 Rin = new BufferedReader(new InputStreamReader(game.getInputStream()));
//                 Rout = new PrintWriter(game.getOutputStream(), true);
//             } 
                    
//             catch (IOException e1) {
//                 e1.printStackTrace();
//             }
//             String createCommand = "JOIN " + playerCount + roomNumber;
//             x = true;
//             System.out.println(x);
//             System.out.println(GameRoom);
//             out.println(createCommand);
//         }
        
//         else if (e.getSource() == create) {
//             playerCount = inputPlayer.getText();
//             roomNumber = inputRoom.getText();
//             String createCommand = "CREATE " + playerCount + " " + roomNumber;
//             x = true;
//             port = Integer.parseInt(playerCount+roomNumber);
//             out.println(createCommand);
//             System.out.println(createCommand);
//             System.out.println("Create button clicked");
//         }

//         else if (e.getSource() == Draw) {
//             score += random.nextInt(11) + 1;

//             System.out.println("Draw Card button clicked");
//         } 
        
//         else if (e.getSource() == Fold) {
//             String foldCommand = "FOLD " + score;
//             out.println(foldCommand);
//             System.out.println("Fold button clicked");
//         } 
        
        
//         else if (e.getSource() == Start) {
            
//             score += random.nextInt(11) + 1;
//             score += random.nextInt(11) + 1;

//             if (score == 21) {
//                 String startCommand = "SCOREX 21";
//                 out.println(startCommand);
//             }
//             else{
//                 String startCommand = "START " + score;
//                 out.println(startCommand);
//             }
//             System.out.println("Start button clicked");
//         }
        
//     }
   
// }

