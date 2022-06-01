import processing.core.PImage;

import java.util.List;

public final class Sapling extends Plant
{

    private int healthLimit;

    public Sapling(
            String id,
            Point position,
            List<PImage> images,
            int actionPeriod,
            int animationPeriod,
            int health,
            int healthLimit)
    {
        super(id, position, images, animationPeriod, actionPeriod, health);

        this.healthLimit = healthLimit;
    }




    public void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler)
    {
        setHealth(getHealth()+1);
        if (!transformSapling(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent((Entity)this,
                    Factory.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }

    public boolean transformSapling(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        if (this.getHealth() <= 0) {
            killPlant(world, scheduler, imageStore);

            return true;
        }
        else if (this.getHealth() >= this.healthLimit)
        {
            SchedulableEntity tree = Factory.createTree("tree_" + this.getId(),
                    this.getPosition(),
                    Util.getNumFromRange(Util.TREE_ACTION_MAX, Util.TREE_ACTION_MIN),
                    Util.getNumFromRange(Util.TREE_ANIMATION_MAX, Util.TREE_ANIMATION_MIN),
                    Util.getNumFromRange(Util.TREE_HEALTH_MAX, Util.TREE_HEALTH_MIN),
                    imageStore.getImageList(Util.TREE_KEY));

            replaceWith(world, scheduler, tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }



}