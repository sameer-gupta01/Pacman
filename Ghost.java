package ghost;

import java.util.Collections;
import java.util.Arrays;
import java.util.ArrayList;
import processing.core.PImage;

public abstract class Ghost extends Moveable {
    
    public static int[] chaserCoords = {0,0};
    public static boolean debugMode = false;
    public static boolean frightenedMode = false;
    public static int frightenedModeTimer = 0;
    public static boolean sodaCanMode = false;
    public static int sodaCanModeTimer = 0;
    protected String prevMove = "";
    protected String illegalMove = "";
    protected boolean eaten = false;
    protected boolean scatterMode = true;
    protected int[] targetCoords = {0,0};
    protected PImage frightenedSprite;

    /**
     * Constructs a ghost entity
     * @param sprite PImage sprite to be displayed on the screen
     * @param isSolid whether the entity can be passed through or not
     * @param pixelX X coordinate
     * @param pixelY Y coordinate
     * @param speed speed of ghost in pixels per second
     * @param frightenedSprite PImage sprite for frightened ghost
     */
    public Ghost(PImage sprite, boolean isSolid, int pixelX, int pixelY, int speed, PImage frightenedSprite) {
        super(sprite, isSolid, pixelX, pixelY, speed);
        this.frightenedSprite = frightenedSprite;
    }
    /**
     * Determines the difference in height and length between the ghost and it's target square
     * @param height height difference between Ghost and player
     * @param length length difference between Ghost and player
     * @param game Instance of the GameEngine class
     * @return String[] containing order of moves it should make at the next intersection from most to least optimal
     */
    public abstract String[] specialisedMove(double height, double length, GameEngine game);

    /**
     * Getter method that returns whether the ghost has been eaten by Waka
     * @return true if this ghost has been eaten, else returns false
     */
    public boolean getIsEaten() {
        return this.eaten;
    }

    /**
     * Setter method that changes whether the ghost is currently in Scatter mode
     */
    public void setScatterMode() {
        this.scatterMode = !this.scatterMode;
    }


    /**
     * Checks whether the current mode has expired yet - swaps modes if it has
     * @param game Instance of the GameEngine class
     * @param setup Instance of the Settings class
     */
    public static void swapModes(GameEngine game, Settings setup) {

        if ((setup.modeTimer/240 % (long) setup.getModeLengths().get(setup.incrementor)) == 0 && setup.modeTimer/240 > 1) { // if time has expired and the timer is not 0

            for (Ghost ghost: game.ghosts) {
                ghost.setScatterMode();
            }
            if (setup.incrementor + 1 < setup.getModeLengths().size()) { // moves on to next timer in list
                setup.incrementor += 1;
            } else { // if the end of the list has been reached, return to beginning of list
                setup.incrementor = 0;
            }

            setup.modeTimer = 0;

        }
    }

    /**
     * Draws the appropriate ghost sprite depending on whether the ghost is in frightened mode
     * Ovverides the abstract draw method in the abstract class Moveable
     * @param app Instance of the App class
     */
    @Override
    public void draw(App app) {

        if (!this.eaten) { // if ghost has not been eaten by Waka
            if (sodaCanMode) {
                if (sodaCanModeTimer % 128 == 0){ // makes ghost flicker between visible and invisible
                    if (frightenedMode){
                        app.image(this.frightenedSprite, this.pixelX - 4, this.pixelY - 4);
                    }
                    else{
                        app.image(this.sprite, this.pixelX - 4, this.pixelY - 4);
                    }
                }
                return; // do not draw ghosts - invisible

            } else if (frightenedMode) {
                app.image(this.frightenedSprite, this.pixelX - 4, this.pixelY - 4);
            } else {
                app.image(this.sprite, this.pixelX - 4, this.pixelY - 4);
            }
        }
    }

    @Override
    public void tick(Map map, GameEngine game) {

        boolean atIntersection = this.atIntersection(map);

        if (sodaCanMode) {
            sodaCanModeTimer += 1;
            if (sodaCanModeTimer/240 >= Settings.sodaCanLength) { // if time has expired
                sodaCanMode = false;
                sodaCanModeTimer = 0;
            }
        }
        
        if (!frightenedMode) { // regular mode
            game.setup.modeTimer += 1;
            String[] nextDirection = this.getNextDirection(game);
            this.move(nextDirection, atIntersection, map);
        } else { // frightened mode
            frightenedModeTimer += 1;

            if (frightenedModeTimer/240 >= Settings.frightenedLength) { // if time has expired
                frightenedMode = false;
                frightenedModeTimer = 0;
            }

            this.frightenedMove(atIntersection, map);

        }
    }

    /**
     * Determines the move pattern of ghosts in frightened mode by randomising their moveset
     * @param atIntersection boolean indicating whether the ghost is currently at an intersection
     * @param map Instance of the Map class
     */
    public void frightenedMove(boolean atIntersection, Map map) {

        ArrayList<String> potentialMoves = new ArrayList<String>(Arrays.asList("up", "left", "right", "down"));
        String[] returnMoves = new String[4];
        returnMoves[3] = this.illegalMove; // ensures previous move is last option
        potentialMoves.remove(this.illegalMove); // removes previous move from the ArrayList as it has already been added to return array
        Collections.shuffle(potentialMoves); // randomises the move by shuffling the ArrayList

        int i = 0;
        while (i<3) { // adds the randomised moveset to return array
            returnMoves[i] = potentialMoves.get(i);
            i+=1;
        }

        this.move(returnMoves, atIntersection, map);

    }

    /**
     * Iterates through the ghost's potential next moves until it can move the ghost in a legal way (no collison and doesn't go back on itself).
     * Moves the ghost by updating its coordinates
     * @param nextDirection String[] containing ghost's next moves in order from most to least optimal
     * @param atIntersection boolean as to whether the ghost is currently at an intersection
     * @param map Instance of the Map class
     */
    public void move(String[] nextDirection, boolean atIntersection, Map map) {

        for (String direction : nextDirection) {
            String move = direction;

            if (!atIntersection) { // if not at intersection, keep moving in current direction
                move = this.prevMove;
            }
            if (direction.equals(this.illegalMove) && atIntersection && !Ghost.frightenedMode) { // skips move if invalid
                continue;
            }

            if (!this.checkCollision(map.getMap(), this, move)) { // if move is valid, update position
                
                if (move.equals("up")) {

                    this.pixelY -= this.speed;
                    this.prevMove = "up";
                    this.illegalMove = "down";

                } else if (move.equals("down")) {

                    this.pixelY += this.speed;
                    this.prevMove = "down";
                    this.illegalMove = "up";

                } else if (move.equals("right")) {

                    this.pixelX += this.speed;
                    this.prevMove = "right";
                    this.illegalMove = "left";

                } else if (move.equals("left")) {

                    this.pixelX -= this.speed;
                    this.prevMove = "left";
                    this.illegalMove = "right";

                }
                break; // prevents any further moves being made as it has already moved
            }

        }
    }

    /**
     * Determines whether the ghost is at an intersection
     * @param map Instance of the Map class
     * @return true if the ghost is at an intersection, otherwise returns false
     */
    public boolean atIntersection(Map map) {

        if (this.prevMove.equals("left") || this.prevMove.equals("right")) { // if previous move is left or right, then will be at intersecion if can move up or down
            if (!this.checkCollision(map.getMap(), this, "up") || !this.checkCollision(map.getMap(), this, "down")) {
                return true;
            }
        } else { // if previous move is up or down, then will be at intersecion if can move left or right
            if (!this.checkCollision(map.getMap(), this, "left") || !this.checkCollision(map.getMap(), this, "right")) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines the height and length difference between the ghost and the player, then passes it to each ghost's specialised move function
     * @param game Instance of the GameEngine class
     * @return String[] containing order of moves the ghost should make at the next intersection from most to least optimal
     */
    public String[] getNextDirection(GameEngine game) {

        double height = this.pixelY - game.player.pixelY; // height diff betwene ghost and player
        double length = this.pixelX - game.player.pixelX; // length diff between ghost and player

        return this.specialisedMove(height, length, game);
    }

    /**
     * Using the height and length difference between the ghost and its target location, it determines it's most optimal move order
     * @param height difference in Y coordinates between the ghost and it's target location
     * @param length difference in X coordinates between the ghost and it's target location
     * @return String[] containing order of moves it should make at the next intersection from most to least optimal
     */
    public String[] directionMaths(double height, double length) {

        if (length >= 0 && height >= 0) { // if target above and to the left
            if (height > length) { // if further away height-wise, then move vertically
                String[] nextDirection = {"up", "left", "right", "down"};
                return nextDirection;
            } else {
                String[] nextDirection = {"left", "up", "right", "down"};
                return nextDirection;
            }
        }

        else if (length >= 0 && height < 0) { // if target is below and to the left
            if (length + height >=0){ // if further away length wise, then move horizontally
                String[] nextDirection = {"left", "down", "up", "right"};
                return nextDirection;
            } else {
                String[] nextDirection = {"down", "left", "up", "right"};
                return nextDirection;
            }
        }

        else if (height >= 0 && length < 0) { // if target is up and to the right
            if (length + height >=0) {
                String[] nextDirection = {"up", "right", "left", "down"};
                return nextDirection;
            } else {
                String[] nextDirection = {"right", "up", "left", "down"};
                return nextDirection;
            }

        } else { // if target is below and to the right
            if (height > length) {
                String[] nextDirection = {"right", "down", "up", "left"};
                return nextDirection;
            } else {
                String[] nextDirection = {"down", "right", "up", "left"};
                return nextDirection;
            }
        }
    }

    /**
     * Draws a line between the ghost and it's target location as long as the ghost is alive and isn't in frightened mode
     * @param app Instance of the app class
     */
    public void debugModeLine(App app) {

        app.stroke(255,255,255); // makes line white

        if (!this.getIsEaten() && !frightenedMode) { // if ghost is alive and isn't frightened
            app.line(this.pixelX + 8, this.pixelY + 8, this.targetCoords[0] + 8, this.targetCoords[1] + 8);
        }
    }

    /**
     * Updates ghost's status when it is eaten
     */
    public void eaten() {
        this.eaten = true;
    }

    /**
     * Resets the positions and status of the ghosts when they collide with the player
     */
    public void resetPositions() {

        this.pixelX = this.startingX;
        this.pixelY = this.startingY;
        this.eaten = false;
        frightenedMode = false;
        sodaCanMode = false;
        sodaCanModeTimer = 0;

    }

}