package tankwar;

import mech.Mech;
import util.Direction;

import java.awt.*;

public class Missile implements Runnable{
    private int x, y;                   //子弹所在坐标
    private Direction dir;              //子弹的方向
    private int speed = 20;            //子弹的速度
    private int power;                  //子弹的威力
    public int SIZE;                   //子弹的宽度和高度
    private Mech mech;                  //子弹所属机甲
    private boolean live = true;

    public Missile(int x, int y, Mech mech) {
        this.x = x + 25 - power/2;
        this.y = y + 25 - power/2;
        this.mech = mech;
        this.power = mech.getPower();
        this.dir = mech.getDir();
        SIZE = mech.getPower();
    }

    public void draw(Graphics g){
        g.setColor(Color.black);
        g.fillOval(x, y, SIZE, SIZE);
    }

    @Override
    public void run() {
        while (true){
            move();
            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void move(){
        switch (dir)
        {
            case U:
                y -= speed;
                break;
            case D:
                y += speed;
                break;
            case L:
                x -= speed;
                break;
            case R:
                x += speed;
                break;
        }
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

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}
