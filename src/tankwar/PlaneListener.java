package tankwar;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * 监听飞机按键事件
 */
public class PlaneListener implements KeyListener {

    private Battleplane battleplane;

    public PlaneListener(Battleplane battleplane) {
        this.battleplane = battleplane;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        battleplane.keyPress_LR(e, KeyEvent.VK_A, KeyEvent.VK_D);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        battleplane.keyReleased_LR(e, KeyEvent.VK_A, KeyEvent.VK_D);
    }
}
