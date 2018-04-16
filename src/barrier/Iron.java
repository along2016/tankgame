package barrier;

import javax.swing.*;

/**
 * 铁墙
 */
public class Iron extends Barrier {

    public Iron(int x, int y) {
        super(x, y);
        setHp(50);
        setFace(new ImageIcon(Iron.class.getResource("/img/iron_1.png")).getImage());
    }
}
