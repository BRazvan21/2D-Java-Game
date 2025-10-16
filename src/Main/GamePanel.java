package Main;

import entity.Player;
import entity.entity;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //SCREEN SETTINGS
    final int oraginalTileSize = 16; //16x16 Tile
    final int scale = 3;

    public final int tileSize = oraginalTileSize * scale; //48x48 tile
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //768 pixels
    public final int screenHeight = tileSize * maxScreenRow; //576 pixels

    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;

    int FPS = 60;

    TileManager tileM = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eHandler = new EventHandler(this);
    Sound sound = new Sound();
    Thread gameThread;

    public Player player = new Player(this,keyHandler);
    public SuperObject obj[] = new SuperObject[10];
    public entity npc[] = new entity[10];

    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;

    public GamePanel() {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame()
    {
        aSetter.setObject();
        aSetter.setNPC();
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void run() {

        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        while(gameThread != null) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if(delta >= 1)
            {
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000)
            {
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {

        if(gameState == playState){
            player.update();

            for(int i = 0 ; i < npc.length ; i++){
                if(npc[i] != null){
                    npc[i].update();
                }
            }
        }
        if(gameState == pauseState){
            //nothing
        }
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //debug
        long drawStart;

        //title screen
        if(gameState == titleState){
            ui.draw(g2);
        }
        else{
            //tile
            tileM.draw(g2);

            //object
            for(int i = 0 ; i < obj.length ; i++) {
                if(obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }

            //NPC
            for(int i = 0 ; i < npc.length ; i++) {
                if(npc[i] != null) {
                    npc[i].draw(g2);
                }
            }

            //player
            player.draw(g2);

            //ui
            ui.draw(g2);

            g2.dispose();
        }
    }

    public void playMusic(int i){

        sound.setFile(i);
        sound.play();
        sound.loop();
    }

    public void stopMusic(){

        sound.stop();
    }
    public void playSE(int i){
        sound.setFile(i);
        sound.play();
    }
}
