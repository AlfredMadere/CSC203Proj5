import processing.core.PImage;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class FamilyDude extends AutonomousDude {

    public FamilyDude(String id, Point position, int animationPeriod, int actionPeriod, int resourceLimit, List<PImage> images, int healthLimit, int startingHealth ) {
        super(id, position, animationPeriod, actionPeriod, resourceLimit, images, healthLimit, startingHealth);

    }



    abstract public FamilyDude _dudeToTransformInto();

    public boolean transform(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore)
    {
        SchedulableEntity miner = _dudeToTransformInto();
        replaceWith(world, scheduler, miner);
        miner.scheduleActions(scheduler, world, imageStore);
        return true;
    }

}
