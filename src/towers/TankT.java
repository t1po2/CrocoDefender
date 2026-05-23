package towers;

public class TankT extends Tower {

    

    public TankT(){
        super(200,3,45,2400,"splitter_proj");

        this.upgrade1Cost = 1200;
        this.upgrade2Cost = 1500;
        this.upgrade3Cost = 4800;
        this.upgrade4Cost = 10000;

        this.upgrade1Desc = "+1 dmg";
        this.upgrade2Desc = "comming soon...";
        this.upgrade3Desc = "-SWITCH TO ATTILERY- range increased, fire rate decreased (4!)";
        this.upgrade4Desc = "Attack is the secret of defense; defense is the planning of an attack. ACTIVATE Railgun Modification (A!)";

    }
    @Override
    public void applyUpgrade1(){
        this.damage +=1;
        lock4=4;
    }
    @Override
    public void applyUpgrade2(){
    }
    @Override
    public void applyUpgrade3(){
        this.range = 380;
        this.fireRate = 60000/20;       //60000 = 1min  then divide with the "real" rpm
        lock3=4;
        lock4=4;
    }
    @Override
    public void applyUpgrade4(){
        this.projectileKey = "laser_proj";
        this.damage = 1000;
        this.fireRate = 60000/5;
        this.range=600;
        lock1=4;
        lock2=4;
        lock3=4;
        lock4=4;
    }
}
