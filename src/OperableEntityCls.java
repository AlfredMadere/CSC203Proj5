import processing.core.PImage;

import java.util.List;

public abstract class OperableEntityCls extends AnimatedEntityCls implements OperableEntity{
    private int actionPeriod;
    public OperableEntityCls(String id, Point position, List<PImage> image, int animationPeriod, int actionPeriod) {
        super(id, position, image, animationPeriod);
        this.actionPeriod = actionPeriod;
    }
    abstract public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler);

    public int getActionPeriod() {
        return actionPeriod;
    }

    public void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore)
    {
        super.scheduleActions(scheduler, world, imageStore);
        scheduler.scheduleEvent(this,
                Factory.createActivityAction( this, world, imageStore),
                this.actionPeriod);

    }

    public void replaceWith(WorldModel world,
                            EventScheduler scheduler, Entity e){
        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);
        world.addEntity(e);

    }
}
