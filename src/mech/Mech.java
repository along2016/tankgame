package mech;

import tankwar.Missile;
import util.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 机甲类
 */
public abstract class Mech {
    //速度
    private int speed = 5;
    //移动方向
    private Direction dir;
    //位置坐标
    private int x ,y;
    //子弹
    private Missile missile;
    //子弹大小
    private int power;
    //机甲方向键的按键情况
    private boolean b_L, b_U, b_R, b_D;

    public Mech(int x, int y, int speed, Direction dir, Missile missile) {
        this.speed = speed;
        this.dir = dir;
        this.x = x;
        this.y = y;
        this.missile = missile;
    }

    /**
     * 绘制机甲
     */
    public void draw(ImageIcon mech, Graphics g){
        g.drawImage(mech.getImage(), x, y, null);
    }

    //按照上下左右四个方向绘制机甲
    public void draw(ImageIcon mech_L, ImageIcon mech_U, ImageIcon mech_R, ImageIcon mech_D, Graphics g){
        switch (dir) {
            case U:
                g.drawImage(mech_U.getImage(), x, y, null);
                break;
            case D:
                g.drawImage(mech_D.getImage(), x, y, null);
                break;
            case L:
                g.drawImage(mech_L.getImage(), x, y, null);
                break;
            case R:
                g.drawImage(mech_R.getImage(), x, y, null);
        }
    }

    /**
     * 机甲移动
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

    public void fire(){
        int missileX = x + 25 - power/2;
        int missileY = y + 25 - power/2;
        missile = new Missile(missileX, missileY, this);
        setMissile(missile);
        new Thread(missile).start();
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

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public Missile getMissile() {
        return missile;
    }

    public void setMissile(Missile missile) {
        this.missile = missile;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
