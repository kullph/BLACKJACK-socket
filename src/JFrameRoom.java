import javax.swing.JFrame;
public class JFrameRoom extends JFrame{
    public JFrameRoom(){
        init();
    }

    public void init(){
        setTitle("ROOM");
        setSize(500,400);
        setVisible(true);
        setLocationRelativeTo(null);
        setResizable(false);
    }
}

