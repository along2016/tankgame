package tankwar;

import mech.Mech;
import util.Direction;
import util.TankLevel;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Tank extends Mech{

    public static final int SIZE = 50;                 //坦克图片大小
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
                        setSpeed(3);
                        setHp(80);
                        setHpMax(80);
                        setFireTime(30l);
                        break;
                    case MIDDLE:
                        setPower(12);
                        setSpeed(4);
                        setHp(100);
                        setHpMax(100);
                        setFireTime(25l);
                        break;
                    case SENIOR:
                        setPower(14);
                        setSpeed(5);
                        setHp(120);
                        setHpMax(120);
                        setFireTime(20l);
                        break;
                }
                break;
            case 2:
                switch (level){
                    case PRIMARY:
                        setPower(12);
                        setSpeed(4);
                        setHp(50);
                        setHpMax(50);
                        setFireTime(30l);
                        break;
                    case MIDDLE:
                        setPower(16);
                        setSpeed(5);
                        setHp(60);
                        setHpMax(60);
                        setFireTime(25l);
                        break;
                    case SENIOR:
                        setPower(20);
                        setSpeed(6);
                        setHp(70);
                        setHpMax(70);
                        setFireTime(20l);
                        break;
                }
                break;
            case 3:
                switch (level){
                    case PRIMARY:
                        setPower(2);
                        setSpeed(6);
                        setHp(40);
                        setHpMax(40);
                        setFireTime(15l);
                        break;
                    case MIDDLE:
                        setPower(3);
                        setSpeed(8);
                        setHp(50);
                        setHpMax(50);
                        setFireTime(10l);
                        break;
                    case SENIOR:
                        setPower(4);
                        setSpeed(10);
                        setHp(60);
                        setHpMax(60);
                        setFireTime(5l);
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
            setHp(getHp()/2);
            setHpMax(getHpMax()/2);
        }

    }

    /**
     * 绘制坦克
     */
    public void drawTank(Graphics g){
        draw(tank_L, tank_U, tank_R, tank_D, g);
    }
}
