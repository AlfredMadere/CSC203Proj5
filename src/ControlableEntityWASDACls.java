import processing.core.PImage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

public class ControlableEntityWASDACls extends Dude implements KeyListener {


    public ControlableEntityWASDACls(String id, Point position, int animationPeriod, int actionPeriod, int resourceLimit, List<PImage> image) {
        super(id, position, animationPeriod, actionPeriod, resourceLimit, image);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }




    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {

    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {

    }

    @Override
    public Dude _dudeToTransformInto() {
        return null;
    }

    @Override
    public void _doAdjacency(WorldModel world, Entity target, EventScheduler scheduler) {

    }
}
