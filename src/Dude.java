import processing.core.PImage;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class Dude extends TargetingEntity{
    private int resourceLimit;
    public Dude(String id, Point position, int animationPeriod, int actionPeriod, int resourceLimit, List<PImage> image ) {
        super(id, position, image, animationPeriod, actionPeriod);
        this.resourceLimit = resourceLimit;
    }

    public int getResourceLimit() {
        return resourceLimit;
    }


    public Point _nextPosition(
            WorldModel world, Point destPos)
    {
        PathingStrategy strategy = new AStarPathingStrategy();
        Predicate<Point> canPassThrough = (p) -> world.withinBounds(p) && (!world.isOccupied(p) || world.isOccupied(p) && world.getOccupancyCell(p).getClass() == Stump.class);
        BiPredicate<Point, Point> withinReach = (p1, p2) -> Point.adjacent(p1, p2);
        List<Point> points = strategy.computePath(getPosition(), destPos, canPassThrough, withinReach, PathingStrategy.CARDINAL_NEIGHBORS);
        if (points.isEmpty()) {
            return getPosition();
        }else{
            return points.get(0);
        }
    }

    abstract public Dude _dudeToTransformInto();

    public boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        SchedulableEntity miner = _dudeToTransformInto();
        replaceWith(world, scheduler, miner);
        miner.scheduleActions(scheduler, world, imageStore);
        return true;
    }

}
