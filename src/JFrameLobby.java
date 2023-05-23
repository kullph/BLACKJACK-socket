import javax.swing.*;
import java.awt.GridLayout;
import java.awt.event.*;

public class JFrameLobby extends JFrame implements ActionListener{
    
    private String userName;
    private int portNum;
    private int roomType;
    private Integer[] room_types = {2,3,4};
    private boolean onCreate = false;
    private boolean onJoin = false;

    private JPanel lobbyPanel = new JPanel(new GridLayout(3, 2));
    private JTextField username = new JTextField();
    private JTextField port = new JTextField();
    private JButton join = new JButton("JOIN ROOM");
    private JButton create = new JButton("CREATE ROOM");
    private JComboBox<Integer> room_type_select = new JComboBox<Integer>(room_types);

    public JFrameLobby(){
        init();
    }

    public void init(){
        setTitle("LOBBY");
        setSize(500,300);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lobbyPanel.add(username);
        lobbyPanel.add(port);
        lobbyPanel.add(join);
        lobbyPanel.add(create);
        lobbyPanel.add(room_type_select);
        

        join.addActionListener(this);
        create.addActionListener(this);

        add(lobbyPanel);
   
    }

    public void actionPerformed(ActionEvent e){
        if(e.getSource() == create){
            this.userName = username.getText();
            this.portNum =  Integer.parseInt(port.getText());
            this.roomType = room_type_select.getItemAt(room_type_select.getSelectedIndex());
        }
        else if(e.getSource() == join){
            this.userName = username.getText();
            this.portNum =  Integer.parseInt(port.getText());
        }
    }

    public void setPort(int newPort){
        this.portNum = newPort;
    }

    public void setUsername(String newUsername){
        this.userName = newUsername;
    }

    public void setRoomType(int newRoomType){
        this.roomType = newRoomType;
    }

    public void setOnCreate(boolean newOnCreate){
        this.onCreate = newOnCreate;
    }

    public void setOnJoin(boolean newOnJoin){
        this.onJoin = newOnJoin;
    }

    public int getPort(){
        return this.portNum;
    }
    
    public String getUsername(){
        return this.userName;
    }

    public int getRoomType(){
        return this.roomType;
    }

    public boolean getOnCreate(){
        return this.onCreate;
    }

    public boolean getOnJoin(){
        return this.onJoin;
    }
}
