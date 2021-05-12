package ghost;
import processing.core.PImage;

public abstract class Moveable extends GameEntity {
    
    protected int speed;

    /**
     * Constructs a Moveable object
     * @param sprite PImage sprite to be displayed on screen
     * @param isSolid boolean indicaiting whether entity is solid
     * @param pixelX X coordinate
     * @param pixelY Y coordinate
     * @param speed speed in pixels per second
     */
    public Moveable(PImage sprite, boolean isSolid, int pixelX, int pixelY, int speed) {
        super(sprite, isSolid, pixelX, pixelY);
        this.speed = speed;
    }

    public Moveable(boolean isSolid, int pixelX, int pixelY, int speed) {
        super(isSolid, pixelX, pixelY);
        this.speed = speed;
    }

    /**
     * Draws moveable entity onto the display
     * @param app Instance of the App class
     */
    public abstract void draw(App app);

    /**
     * Executes the logic for a moveable entity every frame
     * @param map Instance of the Map class
     * @param game Instance of the GameEngine class
     */
    public abstract void tick(Map map, GameEngine game);


    /**
     * Checks whether a collision will occur between a Moveable object and solid objects in the map
     * @param map gameEntity[][] 2-D array which contains the map
     * @param object Moveable object (Waka or Ghost)
     * @param direction direction Moveable object is currenlty moving in
     * @return true if there will be a collison, otherwise returns false
     */
    public boolean checkCollision(GameEntity [][] map, Moveable object, String direction) {
        
        int objectTop = object.pixelY;
        int objectBot = object.pixelY + 16;
        int objectLeft = object.pixelX;
        int objectRight = object.pixelX + 16;

        for (GameEntity [] row : map) {

            for (GameEntity entity : row){

                int entityTop = entity.pixelY;
                int entityBot = entity.pixelY + 16;
                int entityLeft = entity.pixelX;
                int entityRight = entity.pixelX + 16;

                if (entity.isSolid) { // if moveable object can collide with entity
                    if (direction.equals("up")) { // checks to see if corners of entities overlap, resulting in collision
                        if (objectTop - 1 < entityBot && objectBot - 1 >entityTop && objectLeft < entityRight && objectRight > entityLeft){
                            return true;
                        }
                    } else if (direction.equals("down")) {
                        if ( objectTop + 1 < entityBot && objectBot + 1 >entityTop && objectLeft< entityRight && objectRight > entityLeft){
                            return true;
                        }
                    } else if (direction.equals("left")) {
                        if ( objectTop < entityBot && objectBot >entityTop && objectLeft - 1 < entityRight && objectRight - 1 > entityLeft){
                            return true;
                        }
                    } else if (direction.equals("right")) {
                        if (objectTop < entityBot && objectBot >entityTop && objectLeft + 1 < entityRight && objectRight + 1 > entityLeft){
                            return true;
                        }
                    }

                }
            }
        }
        return false;
        
    }


}