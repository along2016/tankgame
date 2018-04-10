package tankwar;

import barrier.*;
import util.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TankWar extends JFrame {
    public static void main(String[] args) {
        new TankWar("map/ermeng.map", 5, 1);
    }

    private static final int AREA_WIDTH = 800;
    private static final int AREA_HEIGHT = 800;
    private ImageIcon background;//战场背景
    private final String map;//战场地图
    private int maxTank;//敌方最大坦克数
    private int style;//坦克型号
    private Tank myTank;//我方坦克
    private Tank enemyTank;//敌方坦克
    private Random r = new Random();
    private List<Tank> allTanks = new ArrayList<>();

//    private Battleplane myPlane = new Battleplane(100, 100, 1, Direction.D, null);

    private List<Wall> walls = new ArrayList<>();//普通墙
    private List<Gold> golds = new ArrayList<>();//金墙
    private List<Iron> irons = new ArrayList<>();//铁墙
    private SelfBorn selfBorn;//我方出生地
    private ArrayList<EnemyBorn> enemyBorns = new ArrayList<EnemyBorn>();//敌方出生地

    private JPanel gamePanel;
    private MyPanel panel;

    public TankWar(String map, int maxTank, int style){
        this.map = map;
        this.maxTank = maxTank;
        this.style = style;
        init();
    }

    //初始化战场
    public void init(){
        setTitle("坦克大战");
        setLocation(300, 400);
        setSize(AREA_WIDTH, AREA_HEIGHT);
        background = new ImageIcon(TankWar.class.getResource("/pic/whiteback.jpg"));
        initMap(new File(map));

        //加入我方坦克
        myTank = new Tank(selfBorn.getX(), selfBorn.getY(), Direction.U, null, true, style);
        allTanks.add(myTank);
        addTank();

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
        setResizable(true);
        setVisible(true);
        addKeyListener(new TankListener(myTank));
//        addKeyListener(new PlaneListener(myPlane));

        new Thread(new WarRepaint()).start();
    }

    private class WarRepaint implements Runnable{
        @Override
        public void run() {
            while (true){
                //每30ms重画一次
                panel.repaint();
                initBattlefield();
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //将战斗单位依次加入战场
    public void initBattlefield(){
//        myPlane.autoMove();
        myTank.move();
        for(int i = 1; i < allTanks.size(); i++){
            allTanks.get(i).autoMove();
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

            //绘制我方坦克出生地
            selfBorn.draw(g);
            myTank.drawTank(g);
            //绘制敌方出生地
            for(int i = 0; i < enemyBorns.size(); i++){
                enemyBorns.get(i).draw(g);
            }
            //绘制所有坦克
            for (int i = 0; i < allTanks.size(); i++){
                allTanks.get(i).drawTank(g);
            }
//            myPlane.drawPlane(g);
            if(myTank.getMissile() != null){
                myTank.getMissile().draw(g);
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

    //向战场中增加坦克
    public void addTank(){
        if(maxTank <= 0){
            return;
        }
        //从敌方坦克出生地持续产生新的敌方坦克，以补充被消灭的坦克
        for(int i = allTanks.size(); i < enemyBorns.size() + 1; i++){
            int temp = r.nextInt(enemyBorns.size());
            EnemyBorn randomEnemyBorn = enemyBorns.get(temp);
            Direction directions[] = Direction.values();
            enemyTank = new Tank(randomEnemyBorn.getX(), randomEnemyBorn.getY(),
                    directions[r.nextInt(directions.length)], null,
                    false, r.nextInt(3) + 1);
            allTanks.add(enemyTank);
            maxTank--;
            if(maxTank <= 0){
                return;
            }
        }
    }
}
