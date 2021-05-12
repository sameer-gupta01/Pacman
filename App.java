package ghost;

import processing.core.PApplet;
import processing.core.PFont;
import java.util.ArrayList;

public class App extends PApplet {

    public static final int WIDTH = 448;
    public static final int HEIGHT = 576;

    public PFont endScreenFont;
    public GameEngine game;

    /**
     * App constructor
     */
    public App() {
        //Set up your objects
        this.game = new GameEngine();
    }

    /**
     * sets up App attributes and configures the game
     */
    public void setup() {
        frameRate(60);
        this.endScreenFont = this.createFont("src/main/resources/PressStart2P-Regular.ttf", 32);
        game.setup.readConfig(); // reads in the configuration file
        game.map.parseMap(this.game, game.setup, this); // parses the map
    }

    /**
     * Configures display width and height
     */
    public void settings() {
        size(WIDTH, HEIGHT);
    }

    /**
     * draws display
     */
    public void draw() { 
        background(0, 0, 0);
        this.game.updateGameState(); // tick
        this.game.drawGameState(this); // draw
    }

    /**
     * detects key pressed and calls appropriate functions
     */
    public void keyPressed() {

        if (keyCode == ' '){ // if spacebar is pressed
            Ghost.debugMode = !Ghost.debugMode;
            return;
        }

        String direction = "";

        if (keyCode == LEFT){
            direction = "left";
        } else if (keyCode == RIGHT){
            direction = "right";
        } else if (keyCode == UP){
            direction = "up";
        } else if (keyCode == DOWN){
            direction = "down";
        }

        if (!direction.equals("")){
            this.game.player.setLastMove(direction);
        }
    }

    public static void main(String[] args) {
        PApplet.main("ghost.App");
    }

}