import java.util.*;
import processing.core.PImage;
//Entity functions and actions

/**
 * An this that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */

public final class DudeNotFull extends Dude implements AnimatedEntity, OperableEntity
{
    private int resourceCount;


    public DudeNotFull(
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int actionPeriod,
            int animationPeriod)
    {
        super(id, position, animationPeriod, actionPeriod, resourceLimit, images);
    }



    //refer to this as super.getanimationperiod


    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> target =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));

        if (!target.isPresent() || !moveTo( world,
                target.get(),
                scheduler)
                || !transform(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent(this,
                    Factory.createActivityAction(this,world, imageStore),
                    this.getActionPeriod());
        }
    }

    public void _doAdjacency(WorldModel world,
                             Entity target,
                             EventScheduler scheduler){
        this.resourceCount += 1;
        if (target instanceof Plant ){
            Plant p = (Plant) target;
            p.setHealth(p.getHealth() -1);
        }
    }


    public boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (this.resourceCount >= this.getResourceLimit()) {
            super.transform(world, scheduler, imageStore);
        }

        return false;
    }

    public Dude _dudeToTransformInto() {
        Dude miner = (Dude)Factory.createDudeFull(this.getId(),
                this.getPosition(), this.getActionPeriod(),
                this.getAnimationPeriod(),
                this.getResourceLimit(),
                this.getImages());

        return miner;
    }






}
