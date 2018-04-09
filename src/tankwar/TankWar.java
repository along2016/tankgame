package tankwar;

import barrier.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class TankWar extends JFrame implements KeyListener {
    public static void main(String[] args) {
        new TankWar().launchFrame();
    }

    private static final int AREA_WIDTH = 600;
    private static final int AREA_HEIGHT = 400;
    //战场背景
    private ImageIcon background;

    private Tank tank = new Tank(50, 50, true, 1);
    //普通墙
    private List<Wall> walls = new ArrayList<>();
    //金墙
    private List<Gold> golds = new ArrayList<>();
    //铁墙
    private List<Iron> irons = new ArrayList<>();
    //我方出生地
    private SelfBorn selfBorn;
    //地方出生地
    private ArrayList<EnemyBorn> enemyBorns = new ArrayList<EnemyBorn>();

    private JPanel gamePanel;
    private MyPanel panel;

    public void launchFrame(){
        initMap(new File("map/ermeng.map"));
        setTitle("坦克大战");
        setLocation(300, 400);
        setSize(AREA_WIDTH, AREA_HEIGHT);
        setBackground(Color.WHITE);
        background = new ImageIcon(TankWar.class.getResource("/pic/whiteback.jpg"));
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
            //添加战场背景
            g.drawImage(background.getImage(), 0, 0 ,null);
            //添加围墙
            for(int i = 0; i < walls.size(); i++){
                walls.get(i).draw(g);
            }
            tank.draw(g);
            if(tank.getMissile() != null){
                tank.getMissile().draw(g);
            }
        }
    }

    //初始化地图
    public void initMap(File file){
        try {
            FileInputStream read = new FileInputStream(file);
            for(int i = 0; i < AREA_HEIGHT/50; i++){
                for(int j = 0; j < AREA_WIDTH/50; j++){
                    switch (read.read()){
                        case 1:
                            walls.add(new Wall(j * 50, i * 50));
                            break;
                        case 2:
                            irons.add(new Iron(j * 50, i * 50));
                            break;
                        case 3:
                            golds.add(new Gold(j * 50, i * 50));
                            break;
                        case 4:
                            selfBorn = new SelfBorn(j * 50, i * 50);
                            break;
                        case 5:
                            enemyBorns.add(new EnemyBorn(j * 50, i * 50));
                            break;
                        case 6:
//                            home = new Home(j * 50, i * 50);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
