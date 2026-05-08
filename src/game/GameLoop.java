package game;

import player.PlayerStats;

public class GameLoop implements Runnable{


    private GameMechanic game;


    private boolean running; // am i running?
    private double updateRate = 1.d/60d;

    private long nextStatTime;
    private int fps, ups;



    public GameLoop(GameMechanic game){
        this.game=game;


    }


    @Override
    public void run(){
        running = true;
        double accumulator = 0;
        long currentTime, lastUpdate = System.currentTimeMillis();
        nextStatTime = System.currentTimeMillis() + 1000;

        while (running) {
            currentTime = System.currentTimeMillis();
            double lastRenderTimeInSec = (currentTime-lastUpdate)/1000d;
            accumulator += lastRenderTimeInSec;
            lastUpdate = currentTime;

            while(accumulator > updateRate){
                update();
                accumulator -= updateRate;
            }
            render();
            printStats();
        }
    }



    public void printStats(){
        if(System.currentTimeMillis() > nextStatTime){
            System.out.println(String.format("FPS: %d UPS: %d",fps,ups));
            fps = 0;
            ups = 0;
            nextStatTime = System.currentTimeMillis() + 1000;
        }
    }

    private void update(){
        game.update();
        ups++;
    }
    private void render(){
        game.render();
        fps++;
    }

}
