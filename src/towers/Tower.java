package towers;

public abstract class Tower {

    protected int range;
    protected int damage;
    protected int fireRate; // milliseconds between shots
    protected int cost;
    protected String towerType;

    public Tower(int range,int damage, int rpm, int cost, String towerType){
        this.range = range;
        this.damage = damage;
        this.fireRate = rpm;
        this.cost = cost;
        this.towerType = towerType;
    }

    public int getRange() {
        return range;
    }

    public int getDamage() {
        return damage;
    }

    public int getFireRate() {
        return fireRate;
    }

    public int getCost() {
        return cost;
    }

    public String getTowerType() {
        return towerType;
    }

    

    
    
}
