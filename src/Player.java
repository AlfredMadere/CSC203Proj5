import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Player extends OperableEntityCls  {
    private int xVelocity = 0;
    private int yVelocity = 0;
    public Player(String id, Point position, int animationPeriod, int actionPeriod, int resourceLimit, List<PImage> image) {
        super(id, position, image, animationPeriod, actionPeriod);
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        updatePosition();
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                this.getActionPeriod());

    }

    public void updatePosition(){
        setPosition(new Point(this.getPosition().x + xVelocity, this.getPosition().y + yVelocity));
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
