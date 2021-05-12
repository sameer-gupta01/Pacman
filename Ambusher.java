package ghost;

import processing.core.PImage;

public class Ambusher extends Ghost {
    
    public Ambusher(PImage sprite, boolean isSolid, int pixelX, int pixelY, int speed, PImage frightenedSprite) {
        super(sprite, isSolid, pixelX, pixelY, speed, frightenedSprite);
    }

    @Override
    public String[] specialisedMove(double height, double length, GameEngine game) {
        
        if (this.scatterMode) { // targets top right corner

            height = this.pixelY;
            length = this.pixelX - 448;
            this.targetCoords[0] = 448;
            this.targetCoords[1] = 0;
            return directionMaths(height, length);

        }

        if (game.player.getCurrentDirection().equals("up")) {

            height = this.pixelY - (game.player.pixelY - 64); // -64 pixels because waka is facing up
            length = this.pixelX - game.player.pixelX;
            this.targetCoords[0] = game.player.pixelX;
            this.targetCoords[1] = game.player.pixelY - 64;

        } else if (game.player.getCurrentDirection().equals("down")) {

            height = this.pixelY - (game.player.pixelY + 64); // +64 pixels because waka is facing down
            length = this.pixelX - game.player.pixelX;
            this.targetCoords[0] = game.player.pixelX;
            this.targetCoords[1] = game.player.pixelY + 64;

        } else if (game.player.getCurrentDirection().equals("left")) {

            height = this.pixelY - game.player.pixelY;
            length = this.pixelX - (game.player.pixelX - 64); // -64 pixels because waka is facing left
            this.targetCoords[0] = game.player.pixelX - 64;
            this.targetCoords[1] = game.player.pixelY;

        } else if (game.player.getCurrentDirection().equals("right")) {

            height = this.pixelY - game.player.pixelY;
            length = this.pixelX - (game.player.pixelX + 64); // +64 pixels because waka is facing right
            this.targetCoords[0] = game.player.pixelX + 64;
            this.targetCoords[1] = game.player.pixelY;

        }

        return directionMaths(height, length);
    }
}