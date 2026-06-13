package player;

import game.Resource;



/**
 * Manages the core player statistics for the tower defense game.
 *
 * <p>This class tracks and controls essential player resources including:
 * <ul>
 *   <li>Player health (HP)</li>
 *   <li>Game currency (Gold)</li>
 * </ul>
 *
 * <p>The class provides methods to:
 * <ul>
 *   <li>Retrieve current player statistics</li>
 *   <li>Update gold reserves (earn or spend)</li>
 *   <li>Process damage taken by the player</li>
 * </ul>
 *
 * <p>PlayerStats uses static fields to maintain a single set of game statistics
 * accessible throughout the application.
 *
 * @see game.Resource
 */
public class PlayerStats {

    
    /** The player's current gold amount. Used to build and upgrade towers. */
    private static int gold = 2400;

    /** The player's current health points. Game ends when this reaches zero. */
    private static int playerHP = 150;

    /**
     * Constructs a PlayerStats object.
     *
     * <p>Note that this class primarily uses static fields, so multiple instances
     * are not necessary and will all reference the same statistics.
     */


    public PlayerStats(){;
    }

    //Getters 
     /**
     * Returns the current amount of gold the player has.
     *
     * @return The current gold amount
     */
    public int getGold(){
        return gold;
    }

    /**
     * Returns the current health points of the player.
     *
     * @return The current player HP
     */
    public int getPlayerHp(){
        return playerHP;
    }

    //Setters
    /**
     * Adds gold to the player's reserves.
     *
     * <p>This method is typically called when the player:
     * <ul>
     *   <li>Defeats enemies</li>
     *   <li>Receives bonus rewards</li>
     *   <li>Sells towers</li>
     * </ul>
     *
     * @param value The amount of gold to add
     */
    public static void addGold(int value){
        gold = gold + value;
    }

    /**
     * Deducts health points from the player when an enemy reaches the end.
     *
     * <p>This method plays a sound effect and reduces the player's health by
     * the specified damage amount.
     *
     * @param dmg The amount of damage to apply to player health
     * @see Resource#playSound(String)
     */
    public void takeDamage(int dmg){
        Resource.playSound("take_damage");
        playerHP = playerHP - dmg;
    }

    /**
     * Deducts gold from the player's reserves.
     *
     * <p>This method is typically called when the player:
     * <ul>
     *   <li>Builds new towers</li>
     *   <li>Upgrades existing towers</li>
     *   <li>Makes game purchases</li>
     * </ul>
     *
     * @param value The amount of gold to deduct
     */
    public static  void removeGold(int value){
        gold = gold - value;
    }
}
