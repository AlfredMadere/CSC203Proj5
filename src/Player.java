import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Player extends OperableEntityCls  {
    private int xVelocity = 0;
    private int yVelocity = 0;
    public Player(String id, Point position, int animationPeriod, int actionPeriod, int resourceLimit, List<PImage> image) {
        super(id, position, image, animationPeriod, actionPeriod);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        updatePosition(world);
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                this.getActionPeriod());

    }

    public void updatePosition(WorldModel world){
        Point newPos = new Point(this.getPosition().x + xVelocity, this.getPosition().y + yVelocity);
        Predicate<Point> canPassThrough = (p) -> world.withinBounds(p) && (!world.isOccupied(p) || world.isOccupied(p) && world.getOccupancyCell(p).getClass() == Stump.class);
        if(canPassThrough.test(newPos)){
            setPosition(newPos);
        }else{
            xVelocity = 0;
            yVelocity = 0;
        }
    }

    public int getxVelocity() {
        return xVelocity;
    }

    public void setxVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public int getyVelocity() {
        return yVelocity;
    }

    public void setyVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }
}
