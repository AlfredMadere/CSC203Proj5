import java.util.*;
import processing.core.PImage;
//Entity functions and actions

/**
 * An this that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */

public final class FamilyDudeNotFull extends FamilyDude {
    private int resourceCount;


    public FamilyDudeNotFull(
            String id,
            Point position,
            List<PImage> images,
            int resourceLimit,
            int actionPeriod,
            int animationPeriod, int healthLimit, int startingHealth)
    {
        super(id, position, animationPeriod, actionPeriod, resourceLimit, images, healthLimit, startingHealth);

    }



    //refer to this as super.getanimationperiod


    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        Optional<Entity> target =
                world.findNearest(this.getPosition(), new ArrayList<>(Arrays.asList(Tree.class, Sapling.class)));


        if (!target.isPresent() || !this.moveTo(world, (Entity)target.get(), scheduler) || !this.transform(world, scheduler, imageStore)) {
            scheduler.scheduleEvent(this, Factory.createActivityAction(this, world, imageStore), (long)this.getActionPeriod());
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

    public FamilyDude _dudeToTransformInto() {
        FamilyDude miner = (FamilyDude)Factory.createDudeFull(this.getId(),
                this.getPosition(), this.getActionPeriod(),
                this.getAnimationPeriod(),
                this.getResourceLimit(),
                this.getImages());

        return miner;
    }






}
