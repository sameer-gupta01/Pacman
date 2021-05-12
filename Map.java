package ghost;

import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import processing.core.PImage;

enum Sprites {

    horizontalWall ("src/main/resources/horizontal.png"),
    verticalWall ("src/main/resources/vertical.png"),
    upLeftWall ("src/main/resources/upLeft.png"),
    upRightWall ("src/main/resources/upRight.png"),
    downLeftWall ("src/main/resources/downLeft.png"),
    downRightWall ("src/main/resources/downRight.png"),
    fruit ("src/main/resources/fruit.png"),
    superFruit ("src/main/resources/SuperFruit.png"),
    ambusher ("src/main/resources/ambusher.png"),
    chaser ("src/main/resources/ghost.png"),
    ignorant ("src/main/resources/ignorant.png"),
    whim ("src/main/resources/whim.png"),
    frightenedGhost ("src/main/resources/frightened.png"),
    sodaCan ("src/main/resources/SodaCan.png");

    private final String image;

    Sprites(String image) {
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }

}

public class Map {

    private GameEntity [][] map = new GameEntity[36][28];

    public GameEntity[][] getMap() {
        return this.map;
    }

    /**
     * reads in a map configuration and will create an instance of each element, assigning it to it's appropriate class
     * @param game instance of the GameEngine class
     * @param settings instance of the Settings class
     * @param app instance of the App class
     */
    public void parseMap(GameEngine game, Settings settings, App app) {

        File f;
        Scanner scan;
        
        try {
            f = new File(settings.getMapFile());
            scan = new Scanner(f);
        } catch (FileNotFoundException e) {
            System.out.println("Map file not found");
            return;
        }


        int row = 0;
        int column = 0;

        while (scan.hasNextLine()) {
            String [] line = scan.nextLine().split("");

            while (column < line.length) {
                
                if (line[column].equals("0")) { // empty space
                    this.map[row][column] = new EmptySpace(null, false, column * 16, row * 16);

                } else if (Arrays.asList("1","2","3","4","5","6").contains(line[column])) { // walls
                    String imageFile = Sprites.values()[Integer.parseInt(line[column]) - 1].getImage();
                    this.map[row][column] = new Wall(app.loadImage(imageFile), true, column * 16, row * 16);

                } else if (line[column].equals("s")) { // soda can
                    this.map[row][column] = new SodaCan(app.loadImage(Sprites.sodaCan.getImage()), false, column * 16, row * 16);

                } else if (line[column].equals("7")) { // fruit
                    this.map[row][column] = new Fruit(app.loadImage(Sprites.fruit.getImage()), false, column * 16, row * 16);
                    Fruit.fruitCount += 1;

                } else if (line[column].equals("8")) { // superfruit
                    this.map[row][column] = new SuperFruit(app.loadImage(Sprites.superFruit.getImage()), false, column * 16, row * 16);
                    Fruit.fruitCount += 1;

                } else if (line[column].equals("p")) { // waka
                    PImage [] sprites = {app.loadImage("src/main/resources/playerLeft.png"),
                                         app.loadImage("src/main/resources/playerClosed.png"),
                                         app.loadImage("src/main/resources/playerDown.png"),
                                         app.loadImage("src/main/resources/playerRight.png"),
                                         app.loadImage("src/main/resources/playerUp.png")
                                        };
                    app.game.player = new Waka(sprites[0], true, settings.getLives(), settings.getSpeed(), sprites, column*16, row*16);
                    this.map[row][column] = new EmptySpace(null, false, column * 16, row * 16);

                } else if (line[column].equals("a")) { // ambusher
                    app.game.ghosts.add(new Ambusher(app.loadImage(Sprites.ambusher.getImage()), true, column * 16, row * 16, settings.getSpeed(), app.loadImage(Sprites.frightenedGhost.getImage())));
                    this.map[row][column] = new EmptySpace(null, false, column * 16, row * 16);

                } else if (line[column].equals("c")) { // chaser
                    app.game.ghosts.add(new Chaser(app.loadImage(Sprites.chaser.getImage()), true, column * 16, row * 16, settings.getSpeed(), app.loadImage(Sprites.frightenedGhost.getImage())));
                    this.map[row][column] = new EmptySpace(null, false, column * 16, row * 16);

                } else if (line[column].equals("i")) { // ignorant
                    app.game.ghosts.add(new Ignorant(app.loadImage(Sprites.ignorant.getImage()), true, column * 16, row * 16, settings.getSpeed(), app.loadImage(Sprites.frightenedGhost.getImage())));
                    this.map[row][column] = new EmptySpace(null, false, column * 16, row * 16);

                } else if (line[column].equals("w")) { // whim
                    app.game.ghosts.add(new Whim(app.loadImage(Sprites.whim.getImage()), true, column * 16, row * 16, settings.getSpeed(), app.loadImage(Sprites.frightenedGhost.getImage())));
                    this.map[row][column] = new EmptySpace(null, false, column * 16, row * 16);
                }

                column += 1;
            }

            row += 1;
            column = 0;
        }
    }

    /**
     * Iterates through the map array and draws each element onto the 448x576 display
     * @param app instance of the App class
     * @param player instance of the Waka class
     */
    public void draw(App app, Waka player) {

        int i = 0;
        int j = 0;

        while (i<this.map.length) {
            while (j < this.map[i].length) {

                if (this.map[i][j].sprite != null) { // will not draw already eaten fruits, sodacans or superfruits or empty spaces
                    app.image(this.map[i][j].sprite, j*16, i*16);
                }
                j += 1;
            }
            i += 1;
            j = 0;
        }

        j = 0;
        for (i=0; i < player.getLives(); i++) { // draws number of lives at bottom of screen
            app.image(player.getSprites()[3], j, 544);
            j += 28;
        }
    }
}
