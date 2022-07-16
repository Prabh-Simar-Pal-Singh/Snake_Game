import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JPanel;
import java.util.Random;
import java.util.Timer.*;

public class gamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    int DELAY = 80 ;
    final int x[] = new int[GAME_UNITS]; //holds the x coordinates of snake body part
    final int y[] = new int[GAME_UNITS]; //holds the y coordinates of snake body part
    int bodyParts = 5;
    int pointsEaten;
    int pointX; // x coordinate of point
    int pointY; // y coordinate of point
    char direction = 'R'; //intialise direction when game start
    boolean running =false;
    Timer timer;
    Random random;

    gamePanel(){
       random = new Random();
       this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
       this.setBackground(Color.black);
       this.setFocusable(true);
       this.addKeyListener(new MyKeyAdapter());
       startGame();
    }

    public void startGame(){

        newPoint();
        running = true;
        timer = new Timer(DELAY,this);
        timer.start();

    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){

        if (running) {

//            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {   //for making grid
//                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//            }

            g.setColor(Color.cyan);   //for making point
            g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
            g.fillOval(pointX, pointY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {   //for making Snake
                if (i == 0) {
                    g.setColor(Color.white);

                    g.fillRoundRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE, 5, 5);

                } else {
                    g.setColor(new Color(45, 180, 0)); // rgb value for new colour
//                    g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.WHITE);
            g.setFont(new Font("POPPIN",Font.BOLD,30));
            FontMetrics fontMetrics = getFontMetrics(g.getFont());
            g.drawString("SCORE : "+pointsEaten,(SCREEN_WIDTH - fontMetrics.stringWidth("SCORE : "+pointsEaten))/2,g.getFont().getSize());

        }
        else gameOver(g);

    }

    public void move(){

        for (int i = bodyParts ; i>0 ; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        switch (direction) { //direction of snake headed
            case 'U': //  U for UP
            y[0]=y[0]-UNIT_SIZE;
            break;

            case 'D':  // D for down
                y[0]=y[0]+UNIT_SIZE;
                break;

            case 'R':    // R for Right
                x[0]=x[0]+UNIT_SIZE;
                break;

            case 'L':   // L for Left
                x[0]=x[0]-UNIT_SIZE;
                break;
        }

    }

    public void newPoint(){

        pointX = random.nextInt((int) (SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
        pointY = random.nextInt((int) (SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;

    }

    public void checkPoint(){

        if ((x[0] == pointX) && (y[0] == pointY)) {
            bodyParts++;
            pointsEaten++;
            DELAY--;
            newPoint();
        }

    }

    public void checkCollision(){

        //this check if head collide with snakebody
        for (int i = bodyParts; i>0 ; i--){
            if ((x[0]==x[i]) && (y[0]==y[i])){
                running = false;
            }
        }

        //this check if head touchs with left boarder
        if (x[0] < 0) {
            running = false;
        }
        //this check if head touchs with Right boarder
        if (x[0] > SCREEN_WIDTH) {
            running = false;
        }
        //this check if head touches with top boarder
        if (y[0] < 0) {
            running = false;
        }
        //this check if head touches with bottonm boarder
        if (y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if (!running) {
            timer.stop();
        }

    }

    public void gameOver(Graphics g){

        //Game Over TEXT
        g.setColor(Color.RED);
        g.setFont(new Font("INK FREE",Font.BOLD,75));
        FontMetrics fontMetrics = getFontMetrics(g.getFont());
        g.drawString("GAME OVER",(SCREEN_WIDTH - fontMetrics.stringWidth("GAME OVER"))/2,SCREEN_HEIGHT/2);

        // SCORe TEXT
        g.setColor(Color.red);
        g.setFont(new Font("INK FREE",Font.BOLD,50));
        FontMetrics fontMetrics2 = getFontMetrics(g.getFont());
        g.drawString("SCORE : "+pointsEaten,(SCREEN_WIDTH - fontMetrics2.stringWidth("SCORE : "+pointsEaten))/2 , g.getFont().getSize()*4);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {

            move();
            checkPoint();
            checkCollision();

        }
        repaint();

    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()) {

                case KeyEvent.VK_LEFT:
                    if (direction != 'R'){
                        direction = 'L';
                    }
                    break;

                case KeyEvent.VK_RIGHT:
                    if (direction != 'L'){
                        direction = 'R';
                    }
                    break;

                case KeyEvent.VK_UP:
                    if (direction != 'D'){
                        direction = 'U';
                    }
                    break;

                case KeyEvent.VK_DOWN:
                    if (direction != 'U'){
                        direction = 'D';
                    }
                    break;
            }
        }
    }

}
