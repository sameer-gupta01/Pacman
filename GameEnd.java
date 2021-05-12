package ghost;
import java.util.ArrayList;

public class GameEnd {

    public static int gameEndTimer = 0;
    /**
     * Displays the Game Over screen for 10 seconds
     * @param app Instance of the App class
     * @param game Instance of GameEngine
     */
    public static void lossScreen(App app, GameEngine game) {

        app.background(0,0,0);
        app.textFont(app.endScreenFont);
        app.text("GAME OVER", 75, 250);

        gameEndTimer += 1;

        if (gameEndTimer/60 == 10) { // checks it has beenn 10 seconds
            resetGame(game, app);
        }

    }

    /**
     * Displays the Win Screen for 10 seconds
     * @param app Instance of the App class
     * @param game Instance of GameEngine
     */
    public static void victoryScreen(App app, GameEngine game) {

        app.background(0,0,0);
        app.textFont(app.endScreenFont);
        app.text("YOU WIN", 100, 250);

        gameEndTimer += 1;

        if (gameEndTimer/60 == 10) {
            resetGame(game, app);
        }

    }

    /**
     * Resets the game
     * @param game Instance of GameEngine
     * @param app Instance of the App class
     */
    public static void resetGame(GameEngine game, App app) {

        game.setup.incrementor = 0; // will go back to first mode timer
        game.ghosts = new ArrayList<Ghost>(); // deletes ghosts from previous game
        game.setup.readConfig();
        game.map.parseMap(game, game.setup, app);
        gameEndTimer = 0;
        game.setup.modeTimer = 0;
        Ghost.debugMode = false;
        Ghost.frightenedMode = false;
        Ghost.sodaCanMode = false;

    }

}