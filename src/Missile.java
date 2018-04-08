import java.awt.*;

public class Missile implements Runnable{
    //子弹所在坐标
    private int x, y;
    //子弹的方向
    private Direction dir;
    //子弹的速度
    private int speed = 20;
    //子弹的宽度和高度
    public int SIZE;
    //子弹所属坦克
    private Tank tank;

    public Missile(int x, int y, Tank tank) {
        this.x = x;
        this.y = y;
        this.tank = tank;
        this.dir = tank.getDir();
        SIZE = tank.getPower();
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
}
