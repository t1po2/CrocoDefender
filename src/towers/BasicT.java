package towers;


public class BasicT extends Tower {

    public BasicT(){
        //int range,int damage, int rpm, int cost, String towerType
        super(200,1,140,1800,"default_proj");
        this.upgrade1Cost = 1200;
        this.upgrade2Cost = 1500;
        this.upgrade3Cost = 1250;
        this.upgrade4Cost = 9999;

        this.upgrade1Desc = "+1 dmg";
        this.upgrade2Desc = "+20% fireRate";
        this.upgrade3Desc = "+3% range";
        this.upgrade4Desc = "Switch to Splitter Prjectile (Not he who has much is rich, but he who gives much (!))";
    }



    @Override
    public void applyUpgrade1(){
        this.damage +=1;
    }
    @Override
    public void applyUpgrade2(){
        this.fireRate = (int)Math.round(fireRate/1.2);  
    }
    @Override
    public void applyUpgrade3(){
        this.range = (int)Math.round(range*1.03);     
    }
    @Override
    public void applyUpgrade4(){
        this.projectileKey = "splitter_proj";
        lock4=4;
    }

}
