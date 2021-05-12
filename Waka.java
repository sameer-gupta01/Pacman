package ghost;

import processing.core.PImage;
import java.util.ArrayList;

public class Waka extends Moveable {

    private boolean isAlive = true;
    public boolean hasWon = false;
    private int lives;
    private PImage [] sprites;
    private String lastMove = "left";
    private String currentDirection = "left";
    private String move = "left";
    private int frameCounter = 1;

    /**
     * Constructs an instance of Waka
     * @param sprite PImage object to be displayed on the screen
     * @param isSolid boolean indicating whether the entity can be passed through
     * @param lives number of lives the player has
     * @param speed speed in pixels per second
     * @param sprites PImage array of all sprites Waka can have
     * @param pixelX X coordinate
     * @param pixelY Y  coordinate
     */
    public Waka(PImage sprite, boolean isSolid, int lives, int speed, PImage[] sprites, int pixelX, int pixelY) {

        super(sprite, isSolid, pixelX, pixelY, speed);
        this.lives = lives;
        this.sprites = sprites;
    }

    public Waka(boolean isSolid, int lives, int speed, int pixelX, int pixelY) { // test constructor
        super(isSolid, pixelX, pixelY, speed);
        this.speed = speed;
    }

    /**
     * Decrements the total fruit count
     */
    public void eatFruit() {
        Fruit.fruitCount -= 1;
    }

    /**
     * sets Waka's current direction
     * @param direction the direction Waka is currently moving in
     */
    public void setCurrentDirection(String direction) {
        this.currentDirection = direction;
    }

    /**
     * sets Waka's lives (used for testing)
     * @param lives The number of lives Waka has
     */
    public void setLives(int lives){
        this.lives = lives;
    }

    /**
     * Getter method that retrieves all of Waka's sprites
     * @return A PImage array containing all of Waka's sprites
     */
    public PImage[] getSprites() {
        return this.sprites;
    }

    /**
     * Getter method that retrives Waka's next move
     * @return Waka's move
     */
    public String getMove() {
        return this.move;
    }

    /**
     * getter method for the last move Waka made
     * @return Waka's last move
     */
    public String getLastMove(){
        return this.lastMove;
    }

    /**
     * Updates Waka's next move
     * @param move Waka's next move
     */
    public void setMove(String move) {
        this.move = move;
    }
    /**
     * Getter method that retrieves whether the player is alive
     * @return true if the player is alive, otherwise returns false
     */
    public boolean getIsAlive() {
        return this.isAlive;
    }

    /**
     * Getter method that retrieves the number of lives the player has left
     * @return The number of lives the player has left
     */
    public int getLives() {
        return this.lives;
    }

    /**
     * Getter method that retrives the frame count for Waka
     * @return frame counter
     */
    public int getFrameCount() {
        return this.frameCounter;
    }

    /**
     * Getter method that retrieves the current direction the player is travelling in
     * @return The direction the player is travelling in
     */
    public String getCurrentDirection() {
        return this.currentDirection;
    }

    /**
     * Updates whether the player is alive
     * @param isAlive true if the player is alive, otherwise false
     */
    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }
    /**
     * Sets last move
     * @param direction Last move made
     */
    public void setLastMove(String direction) {
        this.lastMove = direction;
    }

    @Override
    public void draw(App app) {
        app.image(this.sprite, this.pixelX - 4, this.pixelY - 4);
    }


    @Override
    public void tick(Map map, GameEngine game) {

        this.frameCounter += 1; // keeps track of number of frames
        if (this.frameCounter > 16) {
            this.frameCounter = 0;
        }

        Fruit.checkFruitEaten(map, this); // checks collision with fruits

        if (this.checkGhostCollison(game.ghosts)) { // if waka has collided with ghost
            this.resetPositions(game);
        }

        if (this.checkCollision(map.getMap(), this, this.lastMove) == false) { // if last inputted move does not result in collision
            this.move = this.lastMove;
        } else if (this.checkCollision(map.getMap(), this, this.lastMove) && !this.checkCollision(map.getMap(), this, this.currentDirection)) {
            // if last inputted move results in collision, check if can keep going in current direction
            this.move = this.currentDirection;
        } else { // if both last move inputted and current direction result in collison, stop moving
            return;
        }

        this.move();

    }

    /**
     * Moves the player by updating the player's coordinates
     */
    public void move() {

        if (this.move.equals("up")) {

            this.currentDirection = "up";
            this.pixelY -= this.speed;

        } else if (this.move.equals("down")) {

            this.currentDirection = "down";
            this.pixelY += this.speed;

        } else if (this.move.equals("right")) {

            this.currentDirection = "right";
            this.pixelX += this.speed;

        } else if (this.move.equals("left")) {

                this.currentDirection = "left";
                this.pixelX -= this.speed;

        }
    }

    /**
     * Resets the player's position when it collides with a ghost
     * @param game Instance of the GameEngine class
     */
    public void resetPositions(GameEngine game) {

        this.lives -= 1;
        this.pixelX = this.startingX;
        this.pixelY = this.startingY;
        this.lastMove = "left"; // waka starts by default going left
        this.currentDirection = "left";
        this.move = "left";

        for (Ghost ghost : game.ghosts) {
            ghost.resetPositions();
        }
    }

    /**
     * Checks whether the player has collided with a ghost
     * @param ghosts ArrayList containing all the ghosts in the game
     * @return true if the player has collided with a ghost, otherwise false
     */
    public boolean checkGhostCollison(ArrayList<Ghost> ghosts) {

        int wakaTop = this.pixelY;
        int wakaBot = this.pixelY + 16;
        int wakaLeft = this.pixelX;
        int wakaRight = this.pixelX + 16;

        ArrayList<Boolean> collisionStatus = new ArrayList<Boolean>();

        for (Ghost ghost : ghosts) {

            int ghostTop = ghost.pixelY;
            int ghostBot = ghost.pixelY + 16;
            int ghostLeft = ghost.pixelX;
            int ghostRight = ghost.pixelX + 16;

            // if the ghost and Waka occupy the same 16*16 square
            if (wakaTop < ghostBot && wakaBot > ghostTop && wakaLeft < ghostRight && wakaRight > ghostLeft) {

                if (Ghost.frightenedMode) {
                    ghost.eaten();
                    continue;
                }
                if (ghost.getIsEaten()) {
                    continue;
                }

                collisionStatus.add(true);

            }
        }

        // if I return if waka collides with one ghost, prevents mutliple ghosts from being eaten in frightened mode if they are stacked, as doesn't check them all
        if (collisionStatus.contains(true)){
            return true;
        }

        return false;

    }

    /**
     * Updates the player's sprite every 8 frames to animate Waka
     */
    public void updateSprite() {

        if (this.frameCounter < 8) { // alternates every 8 frames
            this.sprite = this.sprites[1];
            return;
        }

        if (this.currentDirection.equals("left")) {

            this.sprite = this.sprites[0];

        } else if (this.currentDirection.equals("right")) {

            this.sprite = this.sprites[3];

        } else if (this.currentDirection.equals("up")) {

            this.sprite = this.sprites[4];

        } else if (this.currentDirection.equals("down")) {

            this.sprite = this.sprites[2];

        }
    }
}