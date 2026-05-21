package towers;

public class DuckT extends Tower {


    public DuckT(){
        super(300, 1, 430, 5200,"default_proj");
        this.upgrade1Cost = 800;
        this.upgrade2Cost = 1500;
        this.upgrade3Cost = 3000;
        this.upgrade4Cost = 4000;

        this.upgrade1Desc = "+1 dmg";
        this.upgrade2Desc = "+20% fireRate";
        this.upgrade3Desc = "+10% range";
        this.upgrade4Desc = "Enable Splitter Projectiles";
    }


    @Override
    public void applyUpgrade1(){
        this.damage +=1;
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
        this.projectileKey="splitter_proj";
        this.lock4 = 4;
    }
    
}
