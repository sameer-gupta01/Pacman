package ghost;

import processing.core.PImage;

public class Chaser extends Ghost {
    
    public Chaser(PImage sprite, boolean isSolid, int pixelX, int pixelY, int speed, PImage frightenedSprite) {
        super(sprite, isSolid, pixelX, pixelY, speed, frightenedSprite);
    }


    @Override
    public String[] specialisedMove(double height, double length, GameEngine game) {

        Ghost.chaserCoords[0] = this.pixelX;
        Ghost.chaserCoords[1] = this.pixelY;

        if (this.scatterMode) { // targets top left corner
            height = this.pixelY;
            length = this.pixelX;
            this.targetCoords[0] = 0;
            this.targetCoords[1] = 0;
        } else { // targets player
            this.targetCoords[0] = game.player.pixelX;
            this.targetCoords[1] = game.player.pixelY;
        }

        return directionMaths(height, length);

    }
}