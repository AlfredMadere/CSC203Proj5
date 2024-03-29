import processing.core.PImage;

import java.awt.*;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Player extends OperableEntityCls implements Killable{
    private int xVelocity = 0;
    private int yVelocity = 0;
    private boolean chopping = false;
    private int resourceCount = 0;
    private int resourceLimit;
    private int planted = 0;
    private int health = 100;
    private int healthLimit;
    public Player(String id, Point position, int animationPeriod, int actionPeriod, int resourceLimit, List<PImage> image) {
        super(id, position, image, animationPeriod, actionPeriod);
        this.resourceLimit = resourceLimit;
    }

    @Override
    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
        updatePosition(world);
        if(chopping){
            tryToChopTree(world);
        }
        emptyWhenNextToHouse(world);

        scheduler.scheduleEvent(this,
                Factory.createActivityAction(this, world, imageStore),
                this.getActionPeriod());

    }

    public void updatePosition(WorldModel world){
        Point newPos = new Point(this.getPosition().x + xVelocity, this.getPosition().y + yVelocity);
        Predicate<Point> canPassThrough = (p) -> world.withinBounds(p) && (!world.isOccupied(p) || (world.isOccupied(p) && world.getOccupancyCell(p).getClass() == Stump.class));
        if(canPassThrough.test(newPos)){
            if(world.isOccupied(newPos) && world.getOccupancyCell(newPos).getClass() == Stump.class){
                world.removeEntity(world.getOccupant(newPos).get());
            }
            world.moveEntity(this, newPos);
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
                if(resourceCount < resourceLimit){
                    resourceCount += 1;
                }
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

    public void plantSapling(Point pressed, WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        Optional<Entity> entityOptional = world.getOccupant(pressed);
        if (!entityOptional.isPresent() && resourceCount > 0) {
            Sapling plantedSapling = (Sapling) Factory.createSapling("planted_sapling_" + planted, pressed, imageStore.getImageList(Util.SAPLING_KEY));
            world.addEntity(plantedSapling);
            plantedSapling.scheduleActions(scheduler, world, imageStore);
            planted++;
            decreaseResources(1);
            //create a sapling(eventually seeds) in the location
        }
    }

    public void decreaseResources (int amt){
        int newResourceCount = resourceCount - amt;
        if(amt < 0){
            resourceCount = 0;
        }else{
            resourceCount = newResourceCount;
        }

    }



    public void attractZombies(WorldModel world, boolean b){
        List<Zombie> zombies = world.getEntities().stream().filter((e) -> e instanceof Zombie).map((e) -> (Zombie) e).toList();
        for(Zombie zombie : zombies){
            zombie.setOnlyTargetPlayer(b);
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

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public void harm(int amt) {
        int newHealth = getHealth() - amt;
        if(newHealth <= 0){
            setHealth(0);
        }else{
            setHealth(newHealth);
        }
    }
}
