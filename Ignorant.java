package ghost;

import processing.core.PImage;

public class Ignorant extends Ghost {
    
    public Ignorant(PImage sprite, boolean isSolid, int pixelX, int pixelY, int speed, PImage frightenedSprite) {
        super(sprite, isSolid, pixelX, pixelY, speed, frightenedSprite);
    }

    @Override
    public String[] specialisedMove(double height, double length, GameEngine game) {
        
        double straightLineDistance = Math.sqrt(Math.pow(height, 2) + Math.pow(length, 2));

        if (this.scatterMode) { // targets bottom left
            height = this.pixelY - 576;
            length = this.pixelX - 0;
            this.targetCoords[0] = 0;
            this.targetCoords[1] = 576;
            return directionMaths(height, length);
        }

        if (straightLineDistance >= 128) { // if more than 8 units away, target Waka
            this.targetCoords[0] = game.player.pixelX;
            this.targetCoords[1] = game.player.pixelY;
        } else { // target bottom left
            height = this.pixelY - 576;
            length = this.pixelX - 0;
            this.targetCoords[0] = 0;
            this.targetCoords[1] = 576;
        }

        return directionMaths(height, length);

    }
}