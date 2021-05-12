package ghost;
import processing.core.PImage;

public class SodaCan extends Fruit {

    public SodaCan(PImage sprite, boolean isSolid, int pixelX, int pixelY) {
        super(sprite, isSolid, pixelX, pixelY);
    }

    /**
     * Adjusts the game state when a Soda Can is consumed by Waka
     */
    public static void sodaCanEaten() {
        Ghost.sodaCanMode = true;
        Ghost.sodaCanModeTimer = 0;
    }
}