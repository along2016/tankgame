package mech;

import tankwar.Missile;
import tankwar.Tank;
import tankwar.TankWar;
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
    protected boolean b_L, b_U, b_R, b_D;

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

    //向左移动
    public void moveLeft(){
        x -= speed;
    }
    //向右移动
    public void moveRight(){
        x += speed;
    }
    //向上移动
    public void moveUp(){
        y -= speed;
    }
    //向下移动
    public void moveDown(){
        y += speed;
    }

    /**
     * 机甲自动移动
     */
    public void autoMove(){
        if(dir == Direction.U){
            //向上移动
            y -= speed;
        } else if(dir == Direction.D){
            //向下移动
            y += speed;
        } else if(dir == Direction.L){
            //向左移动
            x -= speed;
        } else if(dir == Direction.R){
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

    /**
     * 响应键盘按键监听事件的方法
     * （上下左右键）
     * @param event
     * @param keyCode_up 向上方向键的键值
     * @param keyCode_down 向下方向键的键值
     * @param keyCode_left 向左方向键的键值
     * @param keyCode_right 向右方向键的键值
     * @param pressed  true:按键按下  false:按键释放
     */
    public void keyListener(KeyEvent event, int keyCode_up, int keyCode_down,
                            int keyCode_left, int keyCode_right, boolean pressed){
        int keyCode = event.getKeyCode();
        if(keyCode == keyCode_up){
            b_U = pressed;
        } else if(keyCode == keyCode_down){
            b_D = pressed;
        } else if(keyCode == keyCode_left){
            b_L = pressed;
        } else if(keyCode == keyCode_right){
            b_R = pressed;
        }
    }

    public void keyPress(KeyEvent event, int keyCode_up, int keyCode_down,
                         int keyCode_left, int keyCode_right){
        keyListener(event, keyCode_up, keyCode_down, keyCode_left, keyCode_right, true);
        moveDirection();
    }

    public void keyReleased(KeyEvent event, int keyCode_up, int keyCode_down,
                            int keyCode_left, int keyCode_right){
        keyListener(event, keyCode_up, keyCode_down, keyCode_left, keyCode_right, false);
    }

    /**
     * 响应左、右方向键键盘按键监听事件的方法
     * （W，A，S，D键）
     * @param keyCode_left 左键对应的按键码
     * @param keyCode_right 右键对应的按键码
     * @param pressed  true:按键按下  false:按键释放
     */
    public void keyListener_LR(KeyEvent event, int keyCode_left, int keyCode_right, boolean pressed){
        int keyCode = event.getKeyCode();
        if(keyCode == keyCode_left){
            b_L = pressed;
        } else if(keyCode == keyCode_right){
            b_R = pressed;
        }
    }

    public void keyPress_LR(KeyEvent event, int keyCode_left, int keyCode_right){
        keyListener_LR(event, keyCode_left, keyCode_right, true);
        moveDirection_LR();
    }

    public void keyReleased_LR(KeyEvent event, int keyCode_left, int keyCode_right){
        keyListener_LR(event, keyCode_left, keyCode_right, false);
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

    /**
     * 机甲只按照左右方向移动
     */
    private void moveDirection_LR(){
        if(b_L && !b_D){
            dir = Direction.L;
        }else if(b_R && !b_L){
            dir = Direction.R;
        }
    }

    /**
     * 设置敌方坦克的初始方向
     */
    public void defaultDirection(){
        if(getX() <= 0){
            b_L = false;
            b_U = false;
            b_R = true;
            b_D = false;
            setDir(Direction.R);
        }
        if(getY() <= 0){
            b_L = false;
            b_U = false;
            b_R = false;
            b_D = true;
            setDir(Direction.D);
        }
        if(getX() + Tank.SIZE >= TankWar.AREA_WIDTH){
            b_L = true;
            b_U = false;
            b_R = false;
            b_D = false;
            setDir(Direction.L);
        }
        if(getY() + Tank.SIZE >= TankWar.AREA_HEIGHT){
            b_L = false;
            b_U = true;
            b_R = false;
            b_D = false;
            setDir(Direction.U);
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

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public boolean isB_L() {
        return b_L;
    }

    public void setB_L(boolean b_L) {
        this.b_L = b_L;
    }

    public boolean isB_U() {
        return b_U;
    }

    public void setB_U(boolean b_U) {
        this.b_U = b_U;
    }

    public boolean isB_R() {
        return b_R;
    }

    public void setB_R(boolean b_R) {
        this.b_R = b_R;
    }

    public boolean isB_D() {
        return b_D;
    }

    public void setB_D(boolean b_D) {
        this.b_D = b_D;
    }
}
