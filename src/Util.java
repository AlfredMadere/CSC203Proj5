import java.util.*;

import processing.core.PImage;
import processing.core.PApplet;

/**
 * This class contains many functions written in a procedural style.
 * You will reduce the size of this class over the next several weeks
 * by refactoring this codebase to follow an OOP style.
 */
public final class Util
{

    public static final int DASHBOARD_WIDTH = 200;
    public static final int DASHBOARD_HEIGHT = 100;


    public static final int FAMILY_HEALTH_LIM = 6;
    public static final int FAMILY_STARTING_HEALTH = 6;
    public static final int HOUSE_WOOD_REQ = 100;

    public static final String SAPLING_KEY = "sapling";
    public static final int SAPLING_HEALTH_LIMIT = 5;
    public static final int SAPLING_ACTION_ANIMATION_PERIOD = 1000; // have to be in sync since grows and gains health at same time


    public static final String STUMP_KEY = "stump";

    public static final String TREE_KEY = "tree";

    public static final int TREE_ANIMATION_MAX = 600;
    public static final int TREE_ANIMATION_MIN = 50;
    public static final int TREE_ACTION_MAX = 1400;
    public static final int TREE_ACTION_MIN = 1000;
    public static final int TREE_HEALTH_MAX = 3;
    public static final int TREE_HEALTH_MIN = 1;
    public static final String FASTER_FLAG = "-faster";
    public static final int TIMER_ACTION_PERIOD = 100;
    public static final int VIEW_WIDTH = 640;
    public static final int VIEW_HEIGHT = 480;
    public static final int TILE_WIDTH = 32;
    public static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
    public static final int WORLD_WIDTH_SCALE = 2;
    public static final int WORLD_HEIGHT_SCALE = 2;
    public static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
    public static final int TILE_HEIGHT = 32;
    public static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
    public static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;
    public static final String IMAGE_LIST_FILE_NAME = "imagelist";
    public static final String DEFAULT_IMAGE_NAME = "background_default";
    public static final int DEFAULT_IMAGE_COLOR = 0x808080;
    public static final String FAST_FLAG = "-fast";
    public static final String FASTEST_FLAG = "-fastest";
    public static final double FAST_SCALE = 0.5;
    public static final double FASTER_SCALE = 0.25;
    public static final double FASTEST_SCALE = 0.10;
    public static final String INTRO_IMAGE_NAME = "images/testIntroScreen.png" ;
    public static String LOAD_FILE_NAME = "world.sav";
    public static double timeScale = 1.0;


    //this can stay
    public static int getNumFromRange(int max, int min)
    {
        Random rand = new Random();
        return min + rand.nextInt(
                max
                        - min);
    }





}
