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
        if (!transform(world, scheduler, imageStore))
        {
            scheduler.scheduleEvent((Entity)this,
                    Factory.createActivityAction(this, world, imageStore),
                    this.getActionPeriod());
        }
    }


    public boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {

        super.transform(world, scheduler, imageStore);
        if (this.getHealth() >= this.healthLimit)
        {
            //very hacky but we want to be creating new zombie style trees for gameplay
            SchedulableEntity tree = Factory.createTree("tree_" + this.getId(),
                    this.getPosition(),
                    Util.getNumFromRange(Util.TREE_ACTION_MAX, Util.TREE_ACTION_MIN),
                    Util.getNumFromRange(Util.TREE_ANIMATION_MAX, Util.TREE_ANIMATION_MIN),
                    Util.getNumFromRange(Util.TREE_HEALTH_MAX, Util.TREE_HEALTH_MIN),
                    imageStore.getImageList(Util.TREE_KEY + "Z"));

            replaceWith(world, scheduler, tree);
            tree.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }



}
