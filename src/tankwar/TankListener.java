package tankwar;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TankListener implements KeyListener {
    private Tank tank;

    public TankListener(Tank tank) {
        this.tank = tank;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            tank.fire();
        } else {
            tank.keyPress(e, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        tank.keyReleased(e, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT);
    }
}
