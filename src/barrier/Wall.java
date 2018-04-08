package barrier;

import javax.swing.*;

public class Wall extends Barrier {
    public Wall(int x, int y) {
        super(x, y);
        setFace(new ImageIcon(Wall.class.getResource("/img/wall_1.png")).getImage());
    }
}
