import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.security.Key;
import java.util.Iterator;
import java.util.Scanner;

import static processing.core.PConstants.*;

public class GamePlayState implements GameState{
    //This should proba all live in Game
    private ImageStore imageStore;
    private WorldModel world;
    private WorldView view;
    private EventScheduler scheduler;
    private static GameState singleton;
    private PApplet screen;


    private long nextTime;

    public static GameState getSingleton (){
        if(singleton == null){
            singleton = new GamePlayState();
        }
        return singleton;
    }
    @Override
    public void Init(Game game) {
        screen = game.getScreen();
        this.imageStore = new ImageStore(
                createImageColored(Util.TILE_WIDTH, Util.TILE_HEIGHT,
                        Util.DEFAULT_IMAGE_COLOR));
        this.world = new WorldModel(Util.WORLD_ROWS, Util.WORLD_COLS,
                Factory.createBackground(Util.DEFAULT_IMAGE_NAME, imageStore.getImageList(Util.DEFAULT_IMAGE_NAME)));
        this.view = new WorldView(Util.VIEW_ROWS, Util.VIEW_COLS, game.getScreen(), world, Util.TILE_WIDTH,
                Util.TILE_HEIGHT);
        this.scheduler = new EventScheduler(Util.timeScale);

        loadImages(Util.IMAGE_LIST_FILE_NAME, imageStore, game.getScreen());
        loadWorld(world, Util.LOAD_FILE_NAME, imageStore);

        scheduleActions(world, scheduler, imageStore);

        nextTime = System.currentTimeMillis() + Util.TIMER_ACTION_PERIOD;
    }



    public static PImage createImageColored(int width, int height, int color) {
        PImage img = new PImage(width, height, PApplet.RGB);
        img.loadPixels();
        for (int i = 0; i < img.pixels.length; i++) {
            img.pixels[i] = color;
        }
        img.updatePixels();
        return img;
    }

    private Point mouseToPoint(Point mouseLoc)
    {
        return view.viewportToWorld(mouseLoc.x/ Util.TILE_WIDTH, mouseLoc.y/ Util.TILE_HEIGHT);
    }

    static void loadImages(
            String filename, ImageStore imageStore, PApplet screen)
    {
        try {
            Scanner in = new Scanner(new File(filename));
            GameLoader.loadImages(in, imageStore, screen);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void loadWorld(
            WorldModel world, String filename, ImageStore imageStore)
    {
        try {
            Scanner in = new Scanner(new File(filename));
            GameLoader.load(in, world, imageStore);
        }
        catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void scheduleActions(
            WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        for (Entity entity : world.getEntities()) {
            //if they cant be casted to an operable entity do this
            if (entity instanceof SchedulableEntity){
                SchedulableEntity oEntity = (SchedulableEntity) entity;
                oEntity.scheduleActions(scheduler, world, imageStore);
            }

        }
    }

    @Override
    public void Cleanup() {
        //unschedule all entities
        Iterator<Entity> iter = world.getEntities().iterator();
        while(iter.hasNext()){
            scheduler.unscheduleAllEvents(iter.next());
            //world.removeEntity(iter.next()); this line causes issues because you cant remove something from a list while iterating over it apparently
        }
    }

    @Override
    public void Pause() {

    }

    @Override
    public void Resume() {

    }

    @Override
    public void HandleEvents(UserEvent event) {
        if(event instanceof MouseEvent){
            MouseEvent mEvent = (MouseEvent) event;
            Point worldRelativeMouse = mouseToPoint(mEvent.getLocation());
            System.out.println("x: " + worldRelativeMouse.x + " y: " + worldRelativeMouse.y);
            world.maybeChangeToZombieMode(worldRelativeMouse, scheduler, imageStore); // code for creating rock barrier should be inside this funciton
            world.getPlayer().plantSapling(worldRelativeMouse, world, imageStore, scheduler);
        }else if(event instanceof KeyBoardEvent){
            KeyBoardEvent kEvent = (KeyBoardEvent) event;
            if(kEvent.isPressed()){
                if(kEvent.getKey() == CODED){
                    shiftViewWithKeys(kEvent);
                }else{
                    controlPlayerWithKeys(kEvent);
                }
            }else{
                stopPlayerMovementBasedOnKeyReleased(kEvent);
            }
        }

    }



    public void stopPlayerMovementBasedOnKeyReleased(KeyBoardEvent kEvent){
        switch (kEvent.getKey()){
            case 'w':
            case 's':
                //set players xvelocity to negative
                world.getPlayer().setyVelocity(0);
                break;
            case 'a':
            case 'd':
                world.getPlayer().setxVelocity(0);
                break;
            case ' ':
                world.getPlayer().stopChopping();
                break;
            case 'y':
                world.getPlayer().attractZombies(world, false);
                break;

        }

    }

    public void controlPlayerWithKeys(KeyBoardEvent kEvent) {
        switch (kEvent.getKey()) {
            case 'w':
                //set players xvelocity to negative
                world.getPlayer().setyVelocity(-1);
                break;
            case 'a':
                world.getPlayer().setxVelocity(-1);
                break;
            case 's':
                world.getPlayer().setyVelocity(1);
                break;
            case 'd':
                world.getPlayer().setxVelocity(1);
                break;
            case ' ':
                world.getPlayer().startChopping();
                break;
            case 'y':
                world.getPlayer().attractZombies(world, true);
                break;

        }
    }

    public void shiftViewWithKeys(KeyBoardEvent kEvent){
        int dx = 0;
        int dy = 0;

        switch (kEvent.getKeyCode()) {
            case UP:
                dy = -1;
                break;
            case DOWN:
                dy = 1;
                break;
            case LEFT:
                dx = -1;
                break;
            case RIGHT:
                dx = 1;
                break;

        }
        view.shiftView(dx, dy);
    }


    @Override
    public void Update(Game game) {
        long time = System.currentTimeMillis();
        if (time >= nextTime) {
            this.scheduler.updateOnTime(time);
            nextTime = time + Util.TIMER_ACTION_PERIOD;
        }

        //this should not be here, this is an event
        if(world.getHouse().isFull()){
            game.ChangeState(WinState.getSingleton());
        }
        if(world.getPlayer().getHealth() <= 0){
            game.ChangeState(LoseState.getSingleton());
        }
    }

    @Override
    public void Draw(Game game) {
        view.drawViewport();
    }

    @Override
    public void changeState(GameState state) {

    }
}
