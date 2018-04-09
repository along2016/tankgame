package tankwar;

import mech.Mech;
import util.Direction;

import javax.swing.*;
import java.awt.*;

public class Battleplane extends Mech {
    private ImageIcon battleplane;

    public Battleplane(int x, int y, int speed, Direction dir, Missile missile) {
        super(x, y, speed, dir, missile);
        initPlane();
    }

    public void initPlane(){
        battleplane = new ImageIcon(Battleplane.class.getResource("/img/plane.png"));
    }

    public void drawPlane(Graphics g){
        draw(battleplane, g);
    }
}
