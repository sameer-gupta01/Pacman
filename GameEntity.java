package ghost;

import processing.core.PImage;

public class GameEntity {
    
    protected int startingX;
    protected int startingY;
    protected PImage sprite;
    protected boolean isSolid;
    protected int pixelX = 0;
    protected int pixelY = 0;

    /**
     * Constructs a gameEntity object
     * @param sprite PImage sprite to be displayed on the screen
     * @param isSolid boolean indicating whether the entity is solid
     * @param pixelX X coordinate
     * @param pixelY Y coordinate
     */
    public GameEntity(PImage sprite, boolean isSolid, int pixelX, int pixelY) {
        this.startingX = pixelX;
        this.startingY = pixelY;
        this.sprite = sprite;
        this.isSolid = isSolid;
        this.pixelX = pixelX;
        this.pixelY = pixelY;
    }

    /**
     * constructor utilised for testing - no PImage objects
     * @param isSolid true if player can pass through the object, otherwise false
     * @param pixelX x coordinate for entity
     * @param pixelY y coordinate for entity
     */
    public GameEntity(boolean isSolid, int pixelX, int pixelY) { // test constructor
        this.isSolid = isSolid;
        this.pixelX = pixelX;
        this.pixelY = pixelY;
    }

}