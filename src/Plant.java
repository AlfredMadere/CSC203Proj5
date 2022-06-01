import processing.core.PImage;

import java.util.List;

public abstract class Plant extends OperableEntityCls implements Killable{
    private int health;
    public Plant(String id, Point position, List<PImage> images, int animationPeriod, int actionPeriod, int health) {
        super(id, position, images, animationPeriod, actionPeriod);
        this.health = health;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    public void killPlant(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {

        Entity stump = Factory.createStump(this.getId(),
                this.getPosition(),
                imageStore.getImageList(Util.STUMP_KEY));

        replaceWith(world, scheduler, stump);
    }




}
