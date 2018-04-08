package tankwar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TankClient extends JFrame implements KeyListener {
    public static void main(String[] args) {
        new TankClient().launchFrame();
    }

    private static final int AREA_WIDTH = 600;
    private static final int AREA_HEIGHT = 400;

    private Tank tank = new Tank(50, 50, true, 1);

    private JPanel gamePanel;
    private MyPanel panel;

    public void launchFrame(){
        setTitle("坦克大战");
        setLocation(300, 400);
        setSize(AREA_WIDTH, AREA_HEIGHT);
        setBackground(Color.WHITE);
        gamePanel = new JPanel(null);
        panel = new MyPanel();
        panel.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.WHITE));
        panel.setSize(AREA_WIDTH, AREA_HEIGHT);
        gamePanel.add(panel);
        add(gamePanel);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        setResizable(false);
        setVisible(true);
        addKeyListener(this);
        new Thread(new MyRepaint()).start();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            tank.fire();
        } else {
            tank.keyPress(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        tank.keyReleased(e);
    }

    private class MyRepaint implements Runnable{

        @Override
        public void run() {
            while (true){
                //每30ms重画一次
                panel.repaint();
                tank.move();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private class MyPanel extends JPanel{
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            tank.draw(g);
            if(tank.getMissile() != null){
                tank.getMissile().draw(g);
            }
        }
    }

}
