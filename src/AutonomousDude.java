import processing.core.PImage;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class AutonomousDude extends TargetingEntity implements Killable{
    int health;
    int healthLimit;
    int resourceLimit;
    public AutonomousDude(String id, Point position, int animationPeriod, int actionPeriod, int resourceLimit, List<PImage> images, int healthLimit, int startingHealth ) {
        super(id, position, images, animationPeriod, actionPeriod);
        this.resourceLimit = resourceLimit;


        }

//    public Point _nextPosition(
//            WorldModel world, Point destPos)
//    {
//        PathingStrategy strategy = new AStarPathingStrategy();
//        Predicate<Point> canPassThrough = (p) -> world.withinBounds(p) && (!world.isOccupied(p) || world.isOccupied(p) && world.getOccupancyCell(p).getClass() == Stump.class);
//        BiPredicate<Point, Point> withinReach = (p1, p2) -> Point.adjacent(p1, p2);
//        List<Point> points = strategy.computePath(getPosition(), destPos, canPassThrough, withinReach, PathingStrategy.CARDINAL_NEIGHBORS);
//        if (points.isEmpty()) {
//            return getPosition();
//        }else{
//            return points.get(0);
//        }
//    }

    @Override
    public void harm(int amt) {
        int newHealth = getHealth() - amt;
        if(newHealth < 0){
            setHealth(0);
        }else{
            setHealth(amt);
        }
    }


    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealthLimit() {
        return healthLimit;
    }

    public void setHealthLimit(int healthLimit) {
        this.healthLimit = healthLimit;
    }

    public int getResourceLimit() {
        return resourceLimit;
    }

    public void setResourceLimit(int resourceLimit) {
        this.resourceLimit = resourceLimit;
    }
}
