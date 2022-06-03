import processing.core.PImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Player extends OperableEntityCls  {
    private int xVelocity = 0;
    private int yVelocity = 0;
    private boolean chopping = false;
    private int resourceCount = 0;
    private int resourceLimit;
    public Player(String id, Point position, int animationPeriod, int actionPeriod, int resourceLimit, List<PImage> image) {
        super(id, position, image, animationPeriod, actionPeriod);
        this.resourceLimit = resourceLimit;
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        updatePosition(world);
        if(chopping && resourceCount < resourceLimit){
            tryToChopTree(world);
        }
        emptyWhenNextToHouse(world);

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

    public void tryToChopTree(WorldModel world){
        //get the adjacent spots and check if there is a tree in any of them
        List<Point> locationsWithTreesAround = PathingStrategy.CARDINAL_NEIGHBORS.apply(getPosition()).filter(p -> world.isOccupied(p) && world.getOccupancyCell(p).getClass() == Tree.class).collect(Collectors.toList());
        if(locationsWithTreesAround.size() > 0){
            Optional<Entity> target = world.getOccupant(locationsWithTreesAround.get(0));
            if(target.isPresent()){
                chopTree(world, target.get());
            }

        }

    }

    //This sucks because this is the same among all dudes, wish i could find a way to pull this up
    public void chopTree(WorldModel world,
                             Entity target){
        //you get the same amount of resources as the tree has health
        if (target instanceof Tree ){
            Tree p = (Tree) target;
            if(p.getHealth() > 0) {
                this.resourceCount += 1;
                p.setHealth(p.getHealth() - 1);
            }
        }
    }

    public void emptyWhenNextToHouse(WorldModel world){
        //if next to house set this.resource count to zero and increase the houses's resource count
        Optional<Entity> target =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(House.class)));
        if (target.isPresent() && Point.adjacent(target.get().getPosition(), getPosition())){
            if(target.get() instanceof House){
                House house = (House) target.get();
                house.increaseResource(resourceCount);
                resourceCount = 0;

            }
        }

    }

    public void startChopping(){
        chopping = true;
    }

    public void stopChopping(){
        chopping = false;
    }

    public void setxVelocity(int xVelocity) {
        this.xVelocity = xVelocity;
    }

    public void setyVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }

    public int getResourceCount() {
        return resourceCount;
    }

    public int getResourceLimit() {
        return resourceLimit;
    }
}
