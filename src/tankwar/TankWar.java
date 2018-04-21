package tankwar;

import barrier.*;
import tankwar.controller.MissileController;
import tankwar.controller.TankController;
import util.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TankWar{
    public static void main(String[] args) {
        new TankWar("map/ermeng.map", 5, 1);
    }

    static boolean TIMEOUT = false;
    private JFrame f;
    private JPanel gamePanel;
    private MyPanel p;
    private Tank myTank;
    public static final int AREA_WIDTH = 810;
    public static final int AREA_HEIGHT = 830;
    private int style;                                                  //坦克型号
    private TankController tankController;
    private MissileController missileController;
    private List<Missile> missiles = new ArrayList<>();
    private List<Tank> allTanks = new ArrayList<>();
    private List<Boom> booms = new ArrayList<>();
    private List<Wall> walls = new ArrayList<>();                       //普通墙
    private List<Gold> golds = new ArrayList<>();                       //金墙
    private List<Iron> irons = new ArrayList<>();                       //铁墙
    private ArrayList<EnemyBorn> enemyBorns = new ArrayList<EnemyBorn>();       //敌方出生地
    private SelfBorn selfBorn;                                          //我方出生地
    private Home home;
    private Tank enemyTank;                                             //敌方坦克
    private Random r;
    private ImageIcon background;                                       //战场背景
    private final String map;                                           //战场地图
    private int tankMax;                                                //敌方最大坦克数


    public TankWar(String map, int tankMax, int style){
        this.map = map;
        this.tankMax = tankMax;
        this.style = style;
        init();
    }

    //初始化战场
    public void init(){
        f = new JFrame("坦克大战");
        gamePanel = new JPanel(null);
        p = new MyPanel();
        r = new Random();
        initMap(new File(map));

        //加入我方坦克
        myTank = new Tank(selfBorn.getX(), selfBorn.getY(),true, style);
        allTanks.add(myTank);
        addTank();
        background = new ImageIcon(TankWar.class.getResource("/pic/whiteback.jpg"));

        p.setBorder(BorderFactory.createEtchedBorder(Color.BLACK, Color.WHITE));
        p.setSize(AREA_WIDTH, AREA_HEIGHT);
        gamePanel.add(p);
        f.add(gamePanel);
        f.setSize(AREA_WIDTH, AREA_HEIGHT);
        f.setResizable(true);
        f.setVisible(true);

        f.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.addKeyListener(new TankListener(myTank));
        new Thread(new WarRepaint()).start();
    }

    private class WarRepaint implements Runnable{
        @Override
        public void run() {
            while (true){
                //每30ms重画一次
                p.repaint();
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
        tankController = new TankController(myTank, allTanks, walls, golds, irons, booms, home);
        tankController.move();
        for(int i = 1; i < allTanks.size(); i++){
            Tank enemyTank = allTanks.get(i);
            tankController = new TankController(enemyTank, allTanks, walls, golds, irons, booms, home);
            tankController.autoMove();
            aI(tankController);
        }
    }

    private class MyPanel extends JPanel{
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(background.getImage(), 0, 0 ,null);
            for(int i = 0; i < walls.size(); i++){
                walls.get(i).draw(g);
            }
            for(int i = 0; i < irons.size(); i++){
                irons.get(i).draw(g);
            }
            for(int i = 0; i < golds.size(); i++){
                golds.get(i).draw(g);
            }
            for(int i = 0; i < enemyBorns.size(); i++){
                enemyBorns.get(i).draw(g);
            }
            home.draw(g);
            selfBorn.draw(g);
            for (int i = 0; i < allTanks.size(); i++){
                allTanks.get(i).drawTank(g);
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
                            home = new Home(j * 50, i * 50);
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
        if(tankMax <= 0){
            return;
        }
        //从敌方坦克出生地持续产生新的敌方坦克，以补充被消灭的坦克
        for(int i = allTanks.size(); i < enemyBorns.size() + 1; i++){
            int temp = r.nextInt(enemyBorns.size());
            EnemyBorn randomEnemyBorn = enemyBorns.get(temp);
            enemyTank = new Tank(randomEnemyBorn.getX(), randomEnemyBorn.getY(), false, r.nextInt(3) + 1);

            allTanks.add(enemyTank);
            tankMax--;
            if(tankMax <= 0){
                return;
            }
        }
    }

    /**
     * 使敌方坦克自动移动
     * （人工智能）
     */
    private void aI(TankController controller){
        if(r.nextInt(40) == 0){
            controller.fire();
        }
    }
}
