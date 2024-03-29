import processing.core.PImage;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class FamilyDude extends AutonomousDude implements Killable{

    public FamilyDude(String id, Point position, int animationPeriod, int actionPeriod, int resourceLimit, List<PImage> images, int healthLimit, int startingHealth ) {
        super(id, position, animationPeriod, actionPeriod, resourceLimit, images, healthLimit, startingHealth);

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

    abstract public FamilyDude _dudeToTransformInto();

    public void transformToZombie(ImageStore imageStore, WorldModel world, EventScheduler scheduler){
        String propString = "zombie zombie_12_5 12 5 4 1000 100";
        String[] properties = propString.split("\\s");

        SchedulableEntity z1 = Factory.createZombie(this.getId() + "spawed", getPosition(),
                Integer.parseInt(properties[GameLoader.ZOMBIE_ACTION_PERIOD]),
                Integer.parseInt(properties[GameLoader.ZOMBIE_ANIMATION_PERIOD]),
                Integer.parseInt(properties[GameLoader.ZOMBIE_LIMIT]),
                imageStore.getImageList(Util.ZOMBIE_KEY));

        replaceWith(world, scheduler, z1);
        z1.scheduleActions(scheduler, world, imageStore);

//        scheduler.unscheduleAllEvents(this);
//        world.removeEntity(this);
    }
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
