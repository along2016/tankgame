package tankwar;

import util.Direction;
import util.TankLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Tank{
    private boolean b_L, b_U, b_R, b_D;

    //速度
    private int speed = 5;
    //方向
    private Direction dir;
    //坦克所在位置坐标
    private int x;
    private int y;
    //坦克的单位血量
    private int hp = 50;
    //坦克的最大血量
    private int hpMax = 50;
    //坦克的威力
    private int power;
    //是否是我方坦克
    private boolean self;
    //坦克型号
    private int style;
    //坦克等级
    private TankLevel level = TankLevel.PRIMARY;

    //定义上下左右四个方向上的坦克图片
    private ImageIcon tank_U;
    private ImageIcon tank_D;
    private ImageIcon tank_L;
    private ImageIcon tank_R;

    //坦克子弹
    private Missile missile;

    public Missile getMissile() {
        return missile;
    }

    public void setMissile(Missile missile) {
        this.missile = missile;
    }

    public Tank(int x, int y, boolean self, int style) {
        this.x = x;
        this.y = y;
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

    //绘图
    public void draw(Graphics g){
        switch (dir) {
            case U:
                g.drawImage(tank_U.getImage(), x, y, null);
                break;
            case D:
                g.drawImage(tank_D.getImage(), x, y, null);
                break;
            case L:
                g.drawImage(tank_L.getImage(), x, y, null);
                break;
            case R:
                g.drawImage(tank_R.getImage(), x, y, null);
        }

    }

    //响应监听事件的方法
    public void keyPress(KeyEvent event){
        int keyCode = event.getKeyCode();
        switch (keyCode){
            case KeyEvent.VK_UP:
                b_U = true;
                break;
            case KeyEvent.VK_DOWN:
                b_D = true;
                break;
            case KeyEvent.VK_LEFT:
                b_L = true;
                break;
            case KeyEvent.VK_RIGHT:
                b_R = true;
                break;
        }
        moveDirection();
    }

    public void keyReleased(KeyEvent event){
        int keyCode = event.getKeyCode();
        switch (keyCode){
            case KeyEvent.VK_LEFT:
                b_L = false;
                break;
            case KeyEvent.VK_UP:
                b_U = false;
                break;
            case KeyEvent.VK_RIGHT:
                b_R = false;
                break;
            case KeyEvent.VK_DOWN:
                b_D = false;
                break;
        }
    }

    /**
     * 设定坦克无法移动的情况
     * @return
     */
    public boolean noMove(){

        return true;
    }

    /**
     * 坦克移动
     */
    public void move(){
        if(b_U && dir == Direction.U){
            //向上移动
            y -= speed;
        } else if(b_D && dir == Direction.D){
            //向下移动
            y += speed;
        } else if(b_L && dir == Direction.L){
            //向左移动
            x -= speed;
        } else if(b_R && dir == Direction.R){
            //向右移动
            x += speed;
        }
    }

    private void moveDirection(){
        if(b_L && !b_D && !b_R && !b_U){
            dir = Direction.L;
        }else if(b_U && !b_L && !b_R && !b_D){
            dir = Direction.U;
        }else if(b_R && !b_U && !b_D && !b_L){
            dir = Direction.R;
        }else if(b_D && !b_R && !b_U && !b_L){
            dir = Direction.D;
        }
    }

    public void fire(){
        int missileX = x + 25 - power/2;
        int missileY = y + 25 - power/2;
        missile = new Missile(missileX, missileY, this);
        setMissile(missile);
        new Thread(missile).start();
    }

    public void initTank(){
        switch (style){
            case 1:
                switch (level){
                    case PRIMARY:
                        power = 10;
                        break;
                    case MIDDLE:
                        power = 12;
                        break;
                    case SENIOR:
                        power = 14;
                        break;
                }
                break;
            case 2:
                switch (level){
                    case PRIMARY:
                        power = 12;
                        break;
                    case MIDDLE:
                        power = 16;
                        break;
                    case SENIOR:
                        power = 20;
                        break;
                }
                break;
            case 3:
                switch (level){
                    case PRIMARY:
                        power = 2;
                        break;
                    case MIDDLE:
                        power = 3;
                        break;
                    case SENIOR:
                        power = 4;
                        break;
                }
                break;
        }
        tank_U = new ImageIcon(Tank.class.getResource("/img/TANK1_self_up_1.png"));
        tank_D = new ImageIcon(Tank.class.getResource("/img/TANK1_self_down_1.png"));
        tank_L = new ImageIcon(Tank.class.getResource("/img/TANK1_self_left_1.png"));
        tank_R = new ImageIcon(Tank.class.getResource("/img/TANK1_self_right_1.png"));
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }
}
