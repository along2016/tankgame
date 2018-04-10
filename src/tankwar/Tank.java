package tankwar;

import mech.Mech;
import util.Direction;
import util.TankLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tank extends Mech{

    //坦克的单位血量
    private int hp = 50;
    //坦克的最大血量
    private int hpMax = 50;
    //是否是我方坦克
    private boolean self;
    //坦克型号
    private int style;
    //坦克等级
    private TankLevel level = TankLevel.PRIMARY;
    //坦克速度
    private static final int speed = 5;

    //定义上下左右四个方向上的坦克图片
    private ImageIcon tank_U;
    private ImageIcon tank_D;
    private ImageIcon tank_L;
    private ImageIcon tank_R;

    public Tank(int x, int y, Direction dir, Missile missile, boolean self, int style) {
        super(x, y, speed, dir, missile);
        this.self = self;
        this.style = style;
        if(self){
            dir = Direction.U;
        } else {
            TankLevel tankLevels[] = TankLevel.values();
            level = tankLevels[new Random().nextInt(tankLevels.length)];
        }
        initTank();
    }

    /**
     * 设定坦克无法移动的情况
     * @return
     */
    public boolean noMove(int willX, int willY){

        return true;
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
        tank_L = new ImageIcon(Tank.class.getResource("/img/TANK1_self_left_1.png"));
        tank_U = new ImageIcon(Tank.class.getResource("/img/TANK1_self_up_1.png"));
        tank_R = new ImageIcon(Tank.class.getResource("/img/TANK1_self_right_1.png"));
        tank_D = new ImageIcon(Tank.class.getResource("/img/TANK1_self_down_1.png"));
    }

    /**
     * 绘制坦克
     */
    public void drawTank(Graphics g){
        draw(tank_L, tank_U, tank_R, tank_D, g);
    }
}
