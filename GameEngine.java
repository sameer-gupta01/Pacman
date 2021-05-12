package ghost;

import java.util.ArrayList;

public class GameEngine {
    
    public Map map = new Map();
    public Settings setup = new Settings();
    public Waka player;
    public ArrayList<Ghost> ghosts = new ArrayList<Ghost>();

    /**
     * updates the game state
     */
    public void updateGameState() {

        if (player.getIsAlive() && !player.hasWon) {

            this.player.tick(this.map, this);
            this.player.updateSprite();

            for (Ghost ghost : this.ghosts) {
                ghost.tick(this.map, this);
            }

            Ghost.swapModes(this, this.setup);

            if (Fruit.fruitCount == 0) { // if player has eaten all fruits
                this.player.hasWon = true;
            }

            if (this.player.getLives() == 0) { // if player has no more lives
                this.player.setIsAlive(false);
            }
        }
    }

    /**
     * draws the game state onto the display
     * @param app Instance of the App class
     */
    public void drawGameState(App app) {

        if (!this.player.hasWon && this.player.getIsAlive()) { // if game has not ended
            this.map.draw(app, this.player);
            this.player.draw(app);

            for (Ghost ghost : this.ghosts) {
                ghost.draw(app);

                if(Ghost.debugMode){
                    ghost.debugModeLine(app); // draws debug line
                }

            }
        } else {

            if(!this.player.getIsAlive()){ // if player has lost
                GameEnd.lossScreen(app, this);
            } else { // if player has won
                GameEnd.victoryScreen(app, this);
            }
        }
    }
}