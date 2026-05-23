package towers;

public class DuckT extends Tower {


    public DuckT(){
        super(300, 1, 380, 5200,"default_proj");
        this.upgrade1Cost = 1400;
        this.upgrade2Cost = 2760;
        this.upgrade3Cost = 3560;
        this.upgrade4Cost = 6000;

        this.upgrade1Desc = "Increase +1 dmg";
        this.upgrade2Desc = "Increase +20% fireRate";
        this.upgrade3Desc = "Increase +10% range";
        this.upgrade4Desc = "-DUCKS INVASION- Splitter Projectile ACTIVATED (!)";
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
