package ghost;
import processing.core.PImage;

public class Fruit extends GameEntity {

    public static int fruitCount = 0;

    public Fruit(PImage sprite, boolean isSolid, int pixelX, int pixelY) {
        super(sprite, isSolid, pixelX, pixelY);
    }


    /**
     * Checks if the player has eaten a fruit or its subtypes
     * @param map Instance of the Map class
     * @param player Instance of the Waka class
     */
    public static void checkFruitEaten(Map map, Waka player) {

        for (GameEntity [] row : map.getMap()) {

            for (GameEntity entity : row) {

                if (entity.sprite != null && !entity.isSolid) { // filters out walls and already eaten fruit
                    if (entity.pixelX == player.pixelX && entity.pixelY == player.pixelY) {
                        entity.sprite = null; // sprite will no longer be drawn

                        if (entity.getClass().equals(SodaCan.class)) {
                            SodaCan.sodaCanEaten();
                            return; // player does not have to eat all soda cans to win, so don't decrement fruit count
                        }

                        if (entity.getClass().equals(SuperFruit.class)) {
                            SuperFruit.superFruitEaten();
                        }

                        player.eatFruit(); // decrements total fruit count
                    }
                }
            }
        }
    }

}