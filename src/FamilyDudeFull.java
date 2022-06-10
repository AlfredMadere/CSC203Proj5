import java.util.*;
import processing.core.PImage;
//Entity functions and actions

/**
 * An this that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */

public final class FamilyDudeFull extends FamilyDude
{

    public FamilyDudeFull(
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int actionPeriod,
            int animationPeriod, int healthLimit, int startingHealth)
    {
        super(id, position, animationPeriod, actionPeriod, resourceLimit, images, healthLimit, startingHealth);


    }

    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> fullTarget =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(House.class)));

        if (fullTarget.isPresent() && moveTo(world,
                fullTarget.get(), scheduler))
        {
            world.getHouse().increaseResource(resourceLimit);
            transform( world, scheduler, imageStore);

        }
        else {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }




    @Override
    public void _doAdjacency(WorldModel world,
                             Entity target,
                             EventScheduler scheduler) {
        //Don't need this for obvious reasons, it is very general but the other objects that need to "do adjacency" require this method
    }

    @Override
    public FamilyDude _dudeToTransformInto() {
        FamilyDude miner = (FamilyDude)Factory.createDudeNotFull(this.getId(),
                this.getPosition(), this.getActionPeriod(),
                this.getAnimationPeriod(),
                getResourceLimit(),
                this.getImages());

        return miner;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }
}
