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

    public void harm(int amt){
        int newHealth = getHealth() - amt;
        if(newHealth < 0){
            setHealth(0);
        }else{
            setHealth(amt);
        }
    }

    public void reduceToStump(
            WorldModel world,
            EventScheduler scheduler,
            ImageStore imageStore) {

        Entity stump = Factory.createStump(this.getId(),
                this.getPosition(),
                imageStore.getImageList(Util.STUMP_KEY + "Z"));

        replaceWith(world, scheduler, stump);

    }

    //write code to transform plant into dead shrub thing
    public void poisonPlant(WorldModel world,
                            EventScheduler scheduler,
                            ImageStore imageStore){
        Entity shrub = Factory.createShrub(this.getId(),
                this.getPosition(), imageStore.getImageList(Util.SHRUB_KEY));

        replaceWith(world, scheduler, shrub);
    }

    //pull up common code to this from sapling and tree
    public boolean transform (WorldModel world,
                           EventScheduler scheduler,
                           ImageStore imageStore){
        if(this.getHealth() <= -9){
            poisonPlant(world, scheduler, imageStore);
        }
        else if(this.getHealth() <= 0) {
            reduceToStump(world, scheduler, imageStore);

            return true;
        }
        return false;
    }



}
