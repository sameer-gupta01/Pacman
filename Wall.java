package ghost;
import processing.core.PImage;

public class Wall extends GameEntity{
    
    public Wall(PImage sprite, boolean isSolid, int pixelX, int pixelY) {
        super(sprite, isSolid, pixelX, pixelY);
    }

    public Wall(boolean isSolid, int pixelX, int pixelY) { // test constructor
        super(isSolid, pixelX, pixelY);
    }

}