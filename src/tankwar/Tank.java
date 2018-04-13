package tankwar;

import mech.Mech;
import util.Direction;
import util.TankLevel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

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

    public Tank(int x, int y, Direction dir, Missile missile, boolean self, int style) {
        super(x, y, speed, dir, missile);
        this.self = self;
        this.style = style;
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

    /**
     * 绘制坦克
     */
    public void drawTank(Graphics g){
        draw(tank_L, tank_U, tank_R, tank_D, g);
    }
}
