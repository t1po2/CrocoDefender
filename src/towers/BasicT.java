package towers;


public class BasicT extends Tower {

    public BasicT(){
        //int range,int damage, int rpm, int cost, String towerType
        super(200,1,80,1800,"Basic Tower");
        this.upgrade1Cost = 800;
        this.upgrade2Cost = 1500;
        this.upgrade3Cost = 3000;
        this.upgrade4Cost = 5000;
    }
    @Override
    public void applyUpgrade1(){
        this.damage +=2;
    }
    @Override
    public void applyUpgrade2(){
        this.damage +=2;
    }
    @Override
    public void applyUpgrade3(){
        this.damage +=2;
    }
    @Override
    public void applyUpgrade4(){
        this.damage +=2;
    }

}
