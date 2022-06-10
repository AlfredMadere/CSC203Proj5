import processing.core.PImage;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public class Zombie extends AutonomousDude{
    private boolean onlyTargetPlayer = false;
    public Zombie(String id, Point position, int animationPeriod, int actionPeriod, int resourceLimit, List<PImage> images, int healthLimit, int startingHealth) {
        super(id, position, animationPeriod, actionPeriod, resourceLimit, images, healthLimit, startingHealth);
    }



    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        Optional<Entity> target =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(FamilyDudeFull.class, FamilyDudeNotFull.class, Player.class)));

        if(onlyTargetPlayer){
           target =
                    world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Player.class)));
        }

        if (target.isPresent())
        {
            moveTo(world,
                    target.get(), scheduler);
        }
        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore), this.getActionPeriod());
    }

    //not pulling up aspects of next position because I want the flexibility to change how the zombies behave easily, like maybe give them some crazy pathing strategy that makes them walk like a zombie
    @Override
    public Point _nextPosition(WorldModel world, Point destPos) {
        PathingStrategy strategy = new AStarPathingStrategy();
        //zombies can pass through plants and it kills them, they don't target them though
        Predicate<Point> canPassThrough = (p) -> world.withinBounds(p) && (!world.isOccupied(p) || ((world.isOccupied(p) && world.getOccupancyCell(p).getClass() == Stump.class) || (world.isOccupied(p) && world.getOccupancyCell(p) instanceof Plant)));

        BiPredicate<Point, Point> withinReach = Point::adjacent;
        List<Point> points = strategy.computePath(getPosition(), destPos, canPassThrough, withinReach, PathingStrategy.CARDINAL_NEIGHBORS);
        if (points.isEmpty()) {
            return getPosition();
        }else{
            //maybe put this inside do adjacency, but its not a target so it wont really get called,
            // puting int in moveTo would be nice but I would then have to overide some hella bullshit
            Point newPos = points.get(0);
            if(world.isOccupied(newPos) && world.getOccupancyCell(newPos) instanceof Plant){
                Plant plantToKill = (Plant) world.getOccupancyCell(newPos);
                plantToKill.setHealth(-10);
            }
            setPosition(newPos);
            return points.get(0);
        }
    }

    @Override
    public void _doAdjacency(WorldModel world, Entity target, EventScheduler scheduler) {
        System.out.println("callto adjacency");
        System.out.println(((Killable) target).getHealth());

        ((Killable) target).harm(3);
    }

    //can later develop capability to only target player
    public void setOnlyTargetPlayer(boolean b){
        onlyTargetPlayer = b;
    }



}
