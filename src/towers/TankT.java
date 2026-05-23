package towers;

public class TankT extends Tower {

    

    public TankT(){
        super(200,3,45,2400,"splitter_proj");

        this.upgrade1Cost = 800;
        this.upgrade2Cost = 1500;
        this.upgrade3Cost = 3000;
        this.upgrade4Cost = 5000;

        this.upgrade1Desc = "+2 dmg";
        this.upgrade2Desc = "+20% fireRate";
        this.upgrade3Desc = "+10% range";
        this.upgrade4Desc = "comming soon...";

    }
    @Override
    public void applyUpgrade1(){
        this.damage +=2;
    }
    @Override
    public void applyUpgrade2(){
        this.fireRate = (int)Math.round(fireRate/1.2);  //20 % fireRate Buff
    }
    @Override
    public void applyUpgrade3(){
        this.range = (int)Math.round(range*1.1);        
    }
    @Override
    public void applyUpgrade4(){
    }
}
