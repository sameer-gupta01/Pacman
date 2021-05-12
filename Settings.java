package ghost;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.FileReader;
import java.io.IOException;

public class Settings {

    public static int frightenedLength;
    public static int sodaCanLength;
    public int incrementor = 0;
    public int modeTimer = 0;
    private String map;
    private int lives;
    private int speed;
    private JSONArray modeLengths;

    /**
     * Getter method that retrieves the map file
     * @return The map file
     */
    public String getMapFile() {
        return this.map;
    }

    /**
     * Getter method that retrieves the number of lives the player will have
     * @return The number of lives the player has
     */
    public int getLives() {
        return this.lives;
    }

    /**
     * Getter method that retrieves the speed of Waka and ghosts
     * @return The speed of Waka and ghosts
     */
    public int getSpeed() {
        return this.speed;
    }

    /**
     * Getter method that retrieves the lengths of Scatter and Chase modes
     * @return JSONArray containing the length of the modes
     */
    public JSONArray getModeLengths() {
        return this.modeLengths;
    }

    /**
     * Reads in the configuration file and retrieves the game setting information
     */
    public void readConfig() {

        JSONParser parser = new JSONParser();
        Object config;

        try {
            config = parser.parse(new FileReader("config.json"));
        } catch (IOException e) {
            return;
        } catch (ParseException e) {
            return;
        }

        JSONObject jsonObject = (JSONObject) config;

        JSONArray modeLengths = (JSONArray) jsonObject.get("modeLengths");
        this.modeLengths = modeLengths;

        String mapFile = (String) jsonObject.get("map");
        this.map = mapFile;

        long lives = (long) jsonObject.get("lives");
        this.lives = (int) lives;

        long speed = (long) jsonObject.get("speed");
        this.speed = (int) speed;

        long frightenedLength = (long) jsonObject.get("frightenedLength");
        Settings.frightenedLength = (int) frightenedLength;

        long sodaCanLength = (long) jsonObject.get("sodaCanLength");
        Settings.sodaCanLength = (int) sodaCanLength;

    }

}