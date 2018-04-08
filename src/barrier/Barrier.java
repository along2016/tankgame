package barrier;

import javax.swing.*;
import java.awt.*;

/**
 * 围墙类
 */
public class Barrier {
    //围墙大小
    public static final int SIZE = 50;
    //围墙坐标
    private int x, y;
    //围墙皮肤
    private ImageIcon face;

    public Barrier(int x, int y){
        this.x = x;
        this.y = y;
        face = new ImageIcon();
    }

    public void draw(Graphics g){
        g.drawImage(face.getImage(), x, y, x + SIZE, y + SIZE,
                0, 0, face.getIconWidth(), face.getIconHeight(), null);
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

    public ImageIcon getFace() {
        return face;
    }

    public void setFace(Image face) {
        this.face.setImage(face);
    }
}
