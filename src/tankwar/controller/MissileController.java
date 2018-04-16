package tankwar.controller;

import barrier.Barrier;
import barrier.Gold;
import barrier.Iron;
import barrier.Wall;
import tankwar.Boom;
import tankwar.Missile;
import tankwar.Tank;

import java.util.List;

/**
 * 子弹控制类
 */
public class MissileController {
    private Missile missile;
    private List<Tank> tanks;
    private List<Boom> booms;
    private List<Wall> walls;
    private List<Iron> irons;
    private List<Gold> golds;

    public MissileController(Missile missile, List<Tank> tanks, List<Wall> walls,
                             List<Iron> irons, List<Gold> golds, List<Boom> booms) {
        this.missile = missile;
        this.tanks = tanks;
        this.walls = walls;
        this.golds = golds;
        this.irons = irons;
        this.booms = booms;
    }

    public void missileRun(){
        missile.run();
        int x = missile.getX();
        int y = missile.getY();
        for(int i = 0; i < walls.size(); i++){
            Wall wall = walls.get(i);
            if(x > wall.getX() && x < wall.getX() + Wall.SIZE
                    && y > wall.getY() && y < wall.getY() + Wall.SIZE){
                wall.setHp(wall.getHp() - missile.getPower());
                if(wall.getHp() <= 0){
                    removeBarrier(wall);
                }
                missile.setLive(false);
            }
        }

        for(int i = 0; i < irons.size(); i++){
            Iron iron = irons.get(i);
            if(x > iron.getX() && x < iron.getX() + iron.SIZE
                    && y > iron.getY() && y < iron.SIZE){
                iron.setHp(iron.getHp() - missile.getPower());
                if(iron.getHp() <= 0){
                    removeBarrier(iron);
                }
            }
        }


    }

    /**
     * 清理障碍物
     */
    public void removeBarrier(Barrier barrier){
        Boom boom = new Boom(barrier.getX(), barrier.getY());
        new Thread(boom);
        booms.add(boom);
        irons.remove(barrier);
    }
}
