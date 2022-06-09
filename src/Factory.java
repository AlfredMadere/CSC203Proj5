import processing.core.PImage;

import java.util.List;

public class Factory {


    public static SchedulableEntity createDudeNotFull(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int resourceLimit,
            List<PImage> images)
    {
        return new FamilyDudeNotFull(id,
                position,
                images,
                resourceLimit,
                actionPeriod,
                animationPeriod, Util.FAMILY_HEALTH_LIM, Util.FAMILY_STARTING_HEALTH);
    }

    public static SchedulableEntity createPlayer(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int resourceLimit,
            List<PImage> images)
    {
        return new Player(id,
                position,
                animationPeriod,
                actionPeriod,
                resourceLimit,
                images
                );
    }

    public static Entity createHouse(
            String id, Point position, List<PImage> images, int fenceReq)
    {
        return new House( id, position, images.get(0), fenceReq, false); //default is a non mega house
    }

    public static Entity createShrub(
            String id, Point position, List<PImage> images)
    {
        return new Shrub( id, position, images.get(0)); //default is a non mega house
    }

    public static Entity createMegaHouse(
            String id, Point position, List<PImage> images, int fenceReq)
    {
        return new House( id, position, images.get(0), fenceReq, true); //default is a non mega house
    }

    public static AnimatedEntity createObstacle(
            String id, Point position, int animationPeriod, List<PImage> images)
    {
        //I am purposefully returning AnimatedEntity even though its only use it gets casted to Entity. This is because it's eassy to cast and in the future it might be usefull to know exactly what type it is
        return new Obstacle( id, position, images,
                animationPeriod);
    }

    public static SchedulableEntity createTree(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int health,
            List<PImage> images)
    {
        return new Tree(id, position, images,
                actionPeriod, animationPeriod, health);
    }



    public static SchedulableEntity createFairy(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            List<PImage> images)
    {
        return new Fairy( id, position, images,
                actionPeriod, animationPeriod);
    }
    public static SchedulableEntity createDudeFull(
            String id,
            Point position,
            int actionPeriod,
            int animationPeriod,
            int resourceLimit,
            List<PImage> images) {
        return new FamilyDudeFull(id, position, images, resourceLimit,
                actionPeriod, animationPeriod, Util.FAMILY_HEALTH_LIM, Util.FAMILY_STARTING_HEALTH);
    }


    public static SchedulableEntity createSapling(
            String id,
            Point position,
            List<PImage> images)
    {
        return new Sapling(id, position, images,
                Util.SAPLING_ACTION_ANIMATION_PERIOD, Util.SAPLING_ACTION_ANIMATION_PERIOD, 0, Util.SAPLING_HEALTH_LIMIT);
    }

    public static Entity createStump(
            String id,
            Point position,
            List<PImage> images)
    {
        return new Stump( id, position, images.get(0));
    }

    public static Action createAnimationAction(AnimatedEntity entity, int repeatCount) {
        return new AnimationAction(entity,
                repeatCount);
    }

    public static Action createActivityAction(
            OperableEntity entity, WorldModel world, ImageStore imageStore)
    {
        return new ActivityAction(entity, world, imageStore);
    }

}
