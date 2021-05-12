package ghost;

import processing.core.PImage;

public class Whim extends Ghost {
    

    public Whim(PImage sprite, boolean isSolid, int pixelX, int pixelY, int speed, PImage frightenedSprite){
        super(sprite, isSolid, pixelX, pixelY, speed, frightenedSprite);
    }

    @Override
    public String[] specialisedMove(double height, double length, GameEngine game) {
        
        if (this.scatterMode) { // targets bottom right
            height = this.pixelY - 576;
            length = this.pixelX - 448;
            this.targetCoords[0] = 448;
            this.targetCoords[1] = 576;
            return directionMaths(height, length);
        }

        int targetX = 0;
        int targetY = 0;

        if (this.chaserExists(game)) { // if chaser exists
            if (game.player.getCurrentDirection().equals("up")) { // target square changes depending on which way Waka is facing

                targetX = (2*game.player.pixelX - Ghost.chaserCoords[0]);
                targetY = (2*(game.player.pixelY - 64) - Ghost.chaserCoords[1]);

            } else if (game.player.getCurrentDirection().equals("down")) {

                targetX = (2*game.player.pixelX - Ghost.chaserCoords[0]);
                targetY = (2*(game.player.pixelY + 64) - Ghost.chaserCoords[1]);

            } else if (game.player.getCurrentDirection().equals("left")) {

                targetX = (2*(game.player.pixelX - 64) - Ghost.chaserCoords[0]);
                targetY = (2*game.player.pixelY - Ghost.chaserCoords[1]);

            } else if (game.player.getCurrentDirection().equals("right")) {

                targetX = (2*(game.player.pixelX + 64) - Ghost.chaserCoords[0]);
                targetY = (2*game.player.pixelY - Ghost.chaserCoords[1]);

            }

            if (targetX<0) {
                this.targetCoords[0] = 0;
            } else {
                this.targetCoords[0] = targetX;
            }
            if (targetY<0) {
                this.targetCoords[1] = 0;
            } else {
                this.targetCoords[1] = targetY;
            }

            height = this.pixelY - Math.abs(targetY);
            length = this.pixelX - Math.abs(targetX);

        } else { // if no chaser exists, become a chaser
            this.targetCoords[0] = game.player.pixelX;
            this.targetCoords[1] = game.player.pixelY;
        }

        return directionMaths(height, length);

    }

    /**
     * Determines whether a Chaser type ghost is still in play, as Whim's movement is dependent on the existence of a Chaser
     * @param game Instance of the GameEngine class
     * @return true if a chaser does exist, else it returns false
     */
    public boolean chaserExists(GameEngine game) {
        for (Ghost ghost : game.ghosts) {
            if (ghost.getClass().equals(Chaser.class) && !ghost.getIsEaten()) {
                return true;
            }
        }
        return false;
    }
}