package tankwar;

import javax.swing.*;
import java.awt.*;

public class Boom implements Runnable {

    private int x;
    private int y;
    private ImageIcon[] boom = new ImageIcon[6];
    private boolean isLive = true;
    private int count = 0;

    public Boom(int x, int y) {
        this.x = x;
        this.y = y;
        for(int i = 1; i<= 5; i++){
            boom[i] = new ImageIcon(Boom.class.getResource("/img/boom_" + i + ".png"));
        }
    }

    public void drawBoom(Graphics g){
        g.setColor(Color.BLACK);
        g.drawImage(boom[count].getImage(), x, y, null);
        if(count == 5){
            isLive = false;
        }
    }

    @Override
    public void run() {
        while (count <= 5){
            count++;
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
