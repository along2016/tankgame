package tankwar;

import barrier.Gold;
import barrier.Iron;
import barrier.Wall;
import mech.Mech;
import util.Direction;
import util.TankLevel;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Tank extends Mech{

    public static final int SIZE = 50;                 //坦克图片大小
    private int hp = 50;                                //坦克的单位血量
    private int hpMax = 50;                             //坦克的最大血量
    private boolean self;                              //是否是我方坦克
    private int style;                                  //坦克型号
    private TankLevel level = TankLevel.PRIMARY;       //坦克等级
    private static final int speed = 5;               //坦克速度

    //定义上下左右四个方向上的坦克图片
    private ImageIcon tank_U;
    private ImageIcon tank_D;
    private ImageIcon tank_L;
    private ImageIcon tank_R;

    //坦克所在的外部环境
    private List<Tank> allTanks;                        //战场中的其他坦克
    private List<Wall> walls;                           //战场中的普通墙
    private List<Gold> golds;                           //战场中的金墙
    private List<Iron> irons;                           //战场中的铁墙


    public Tank(int x, int y, Direction dir, Missile missile,
                List<Tank> allTanks, List<Wall> walls,
                List<Gold> golds, List<Iron> irons,
                boolean self, int style) {
        super(x, y, speed, dir, missile);
        this.self = self;
        this.style = style;
        this.allTanks = allTanks;
        this.walls = walls;
        this.golds = golds;
        this.irons = irons;
        if(!self){
            TankLevel tankLevels[] = TankLevel.values();
            level = tankLevels[new Random().nextInt(tankLevels.length)];
            defaultDirection();
        }
        initTank();
    }

    public void initTank(){
        switch (style){
            case 1:
                switch (level){
                    case PRIMARY:
                        setPower(10);
                        break;
                    case MIDDLE:
                        setPower(12);
                        break;
                    case SENIOR:
                        setPower(14);
                        break;
                }
                break;
            case 2:
                switch (level){
                    case PRIMARY:
                        setPower(12);
                        break;
                    case MIDDLE:
                        setPower(16);
                        break;
                    case SENIOR:
                        setPower(20);
                        break;
                }
                break;
            case 3:
                switch (level){
                    case PRIMARY:
                        setPower(2);
                        break;
                    case MIDDLE:
                        setPower(3);
                        break;
                    case SENIOR:
                        setPower(4);
                        break;
                }
                break;
        }
        int iLevel = level.getLevel();
        if(self){
            tank_L = new ImageIcon(Tank.class.getResource("/img/TANK" + style + "_self_left_" + iLevel + ".png"));
            tank_U = new ImageIcon(Tank.class.getResource("/img/TANK" + style + "_self_up_" + iLevel + ".png"));
            tank_R = new ImageIcon(Tank.class.getResource("/img/TANK" + style + "_self_right_" + iLevel + ".png"));
            tank_D = new ImageIcon(Tank.class.getResource("/img/TANK" + style + "_self_down_" + iLevel + ".png"));
        } else {
            tank_L = new ImageIcon(Tank.class.getResource("/img/TANK" + style + "_enemy_left_" + iLevel + ".png"));
            tank_U = new ImageIcon(Tank.class.getResource("/img/TANK" + style + "_enemy_up_" + iLevel + ".png"));
            tank_R = new ImageIcon(Tank.class.getResource("/img/TANK" + style + "_enemy_right_" + iLevel + ".png"));
            tank_D = new ImageIcon(Tank.class.getResource("/img/TANK" + style + "_enemy_down_" + iLevel + ".png"));
        }

    }

    public void move(){
        if(b_U && getY() > 0 && noMove(getX(), getY() - speed)
                && noMove(getX() + SIZE - 1, getY() - speed)){
            moveUp();
        } else if(b_R && getX() + SIZE < TankWar.AREA_WIDTH
                && noMove(getX() + SIZE + speed, getY())
                && noMove(getX() + SIZE + speed, getY() + SIZE - 1)){
            moveRight();
        } else if(b_D && getY() < TankWar.AREA_HEIGHT - SIZE -30
                && noMove(getX(), getY() + SIZE + speed)
                && noMove(getX() + SIZE - 1, getY() + SIZE + speed)){
            moveDown();
        } else if(b_L && getX() > 0
                && noMove(getX() - speed, getY())
                && noMove(getX() - speed, getY() + SIZE - 1)){
            moveLeft();
        }
    }

    //列出坦克不能移动的情形
    private boolean noMove(int willX, int willY){
        //1、当坦克碰到其他坦克时不能移动
        for(int i = 0; i < allTanks.size(); i++){
            Tank tank = allTanks.get(i);
            if(willX > tank.getX() && willX < tank.getX() + SIZE
                    && willY > tank.getY() &&  willY < tank.getY() + SIZE){
                return false;
            }
        }

        //2、当坦克碰到普通墙时
        for(int i = 0; i < walls.size(); i++){
            Wall wall = walls.get(i);
            if(willX > wall.getX() && willX < wall.getX() + SIZE
                    && willY > wall.getY() &&  willY < wall.getY() + SIZE){
                return false;
            }
        }

        //3、当坦克碰到金墙时
        for(int i = 0; i < golds.size(); i++){
            Gold gold = golds.get(i);
            if(willX > gold.getX() && willX < gold.getX() + SIZE
                    && willY > gold.getY() &&  willY < gold.getY() + SIZE){
                return false;
            }
        }

        //4、当坦克碰到铁墙时
        for(int i = 0; i < irons.size(); i++){
            Iron iron = irons.get(i);
            if(willX > iron.getX() && willX < iron.getX() + SIZE
                    && willY > iron.getY() &&  willY < iron.getY() + SIZE){
                return false;
            }
        }
        return true;
    }

    /**
     * 绘制坦克
     */
    public void drawTank(Graphics g){
        draw(tank_L, tank_U, tank_R, tank_D, g);
    }
}
