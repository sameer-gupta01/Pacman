package ghost;
import processing.core.PImage;

public class SuperFruit extends Fruit {
    
    public SuperFruit(PImage sprite, boolean isSolid, int pixelX, int pixelY) {
        super(sprite, isSolid, pixelX, pixelY);
    }

    /**
     * Updates the game state when a Super Fruit is consumed by Waka
     */
    public static void superFruitEaten() {
        Ghost.frightenedMode = true;
        Ghost.frightenedModeTimer = 0;
    }
}