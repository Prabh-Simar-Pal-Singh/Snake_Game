import javax.swing.*;

public class gameFrame extends JFrame {
    gameFrame(){
//        gamePanel Panel = new gamePanel();
//        this.add(Panel);

        this.add(new gamePanel()); // work same as above
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null); // screen apear in middle
    }
}
