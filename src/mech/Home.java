package mech;

import barrier.Barrier;

import javax.swing.*;
import java.awt.*;

public class Home extends Barrier {
    private boolean live = true;
    public static int MAXHP = 50;
    public Home(int x, int y) {
        super(x, y);
        setHp(MAXHP);
        setFace(new ImageIcon(Home.class.getResource("/img/home_1.png")).getImage());
    }

    //损坏
    public void damage(){
        setFace(new ImageIcon(Home.class.getResource("/img/home_2.png")).getImage());
    }

    @Override
    public void draw(Graphics g) {
        super.draw(g);
        g.setColor(Color.RED);
        g.drawRect(getX(), getY(), SIZE, 5);
        g.fillRect(getX(), getY(), SIZE * getHp()/MAXHP, 5);
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}
