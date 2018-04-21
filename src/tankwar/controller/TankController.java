package tankwar.controller;

import barrier.Gold;
import barrier.Home;
import barrier.Iron;
import barrier.Wall;
import tankwar.Boom;
import tankwar.Missile;
import tankwar.Tank;
import tankwar.TankWar;
import util.Direction;

import java.util.List;

/**
 * 坦克控制类
 */
public class TankController {

    private Tank tank;
    private List<Tank> allTanks;
    private List<Wall> walls;
    private List<Gold> golds;
    private List<Iron> irons;
    private List<Boom> booms;
    private Home home;
    private long fireTime;          //开火时间
    private long noFire;            //停火时间

    public TankController(Tank tank, List<Tank> allTanks, List<Wall> walls,
                          List<Gold> golds, List<Iron> irons, List<Boom> booms, Home home) {
        this.tank = tank;
        this.allTanks = allTanks;
        this.walls = walls;
        this.golds = golds;
        this.irons = irons;
        this.booms = booms;
        this.home = home;
    }

    public void move(){
        if(tank.isB_U() && tank.getDir() == Direction.U && tank.getY() > 0 && noMove(tank.getX(), tank.getY() - tank.getSpeed())
                && noMove(tank.getX() + tank.SIZE - 1, tank.getY() - tank.getSpeed())){
            tank.moveUp();
        } else if(tank.isB_R() && tank.getDir() == Direction.R && tank.getX() + tank.SIZE < TankWar.AREA_WIDTH
                && noMove(tank.getX() + tank.SIZE + tank.getSpeed(), tank.getY())
                && noMove(tank.getX() + tank.SIZE + tank.getSpeed(), tank.getY() + tank.SIZE - 1)){
            tank.moveRight();
        } else if(tank.isB_D() && tank.getDir() == Direction.D && tank.getY() < TankWar.AREA_HEIGHT - tank.SIZE -30
                && noMove(tank.getX(), tank.getY() +tank.SIZE + tank.getSpeed())
                && noMove(tank.getX() + tank.SIZE - 1, tank.getY() + tank.SIZE + tank.getSpeed())){
            tank.moveDown();
        } else if(tank.isB_L() && tank.getDir() == Direction.L && tank.getX() > 0
                && noMove(tank.getX() - tank.getSpeed(), tank.getY())
                && noMove(tank.getX() - tank.getSpeed(), tank.getY() + tank.SIZE - 1)){
            tank.moveLeft();
        }
    }

    public void autoMove(){
        if(tank.isB_U() && tank.getY() > 0 && noMove(tank.getX(), tank.getY() - tank.getSpeed())
                && noMove(tank.getX() + tank.SIZE - 1, tank.getY() - tank.getSpeed())){
            tank.moveUp();
        } else if(tank.isB_R() && tank.getX() + tank.SIZE < TankWar.AREA_WIDTH
                && noMove(tank.getX() + tank.SIZE + tank.getSpeed(), tank.getY())
                && noMove(tank.getX() + tank.SIZE + tank.getSpeed(), tank.getY() + tank.getSpeed() - 1)){
            tank.moveRight();
        } else if(tank.isB_D() && tank.getY() < TankWar.AREA_HEIGHT - tank.SIZE -30
                && noMove(tank.getX(), tank.getY() + tank.SIZE + tank.getSpeed())
                && noMove(tank.getX() + tank.SIZE - 1, tank.getY() + tank.SIZE + tank.getSpeed())){
            tank.moveDown();
        } else if(tank.isB_L() && tank.getX() > 0
                && noMove(tank.getX() - tank.getSpeed(), tank.getY())
                && noMove(tank.getX() - tank.getSpeed(), tank.getY() + tank.SIZE - 1)){
            tank.moveLeft();
        }
    }

    //列出坦克不能移动的情形
    private boolean noMove(int willX, int willY){
        for(int i = 0; i < allTanks.size(); i++){
            Tank tank = allTanks.get(i);
            if(willX > tank.getX() && willX < tank.getX() + tank.SIZE
                    && willY > tank.getY() &&  willY < tank.getY() + tank.SIZE){
                return false;
            }
        }
        for(int i = 0; i < walls.size(); i++){
            Wall wall = walls.get(i);
            if(willX > wall.getX() && willX < wall.getX() + tank.SIZE
                    && willY > wall.getY() &&  willY < wall.getY() + tank.SIZE){
                return false;
            }
        }
        for(int i = 0; i < golds.size(); i++){
            Gold gold = golds.get(i);
            if(willX > gold.getX() && willX < gold.getX() + tank.SIZE
                    && willY > gold.getY() &&  willY < gold.getY() + tank.SIZE){
                return false;
            }
        }
        for(int i = 0; i < irons.size(); i++){
            Iron iron = irons.get(i);
            if(willX > iron.getX() && willX < iron.getX() + tank.SIZE
                    && willY > iron.getY() &&  willY < iron.getY() + tank.SIZE){
                return false;
            }
        }
        return true;
    }

    public void fire(){
        //控制炮弹发射频率
        if(noFire < fireTime){
            return;
        }
        noFire = 0l;
        Missile missile = new Missile(tank.getX(), tank.getY(), tank);
        MissileController controller = new MissileController(missile, allTanks, walls, irons, golds, home, booms);
        controller.missileRun();
        new Thread(missile).start();
    }

    public long getFireTime() {
        return fireTime;
    }

    public void setFireTime(long fireTime) {
        this.fireTime = fireTime;
    }

    public long getNoFire() {
        return noFire;
    }

    public void setNoFire(long noFire) {
        this.noFire = noFire;
    }
}
