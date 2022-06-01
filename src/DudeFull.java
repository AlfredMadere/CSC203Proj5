import java.util.*;
import processing.core.PImage;
//Entity functions and actions

/**
 * An this that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */

public final class DudeFull extends Dude
{

    public DudeFull(
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int actionPeriod,
            int animationPeriod)
    {
        super(id, position, animationPeriod, actionPeriod, resourceLimit, images);


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
    public Dude _dudeToTransformInto() {
        Dude miner = (Dude)Factory.createDudeNotFull(this.getId(),
                this.getPosition(), this.getActionPeriod(),
                this.getAnimationPeriod(),
                this.getResourceLimit(),
                this.getImages());

        return miner;
    }
}
