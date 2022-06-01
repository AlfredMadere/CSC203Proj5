import processing.core.PImage;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.BiPredicate;
import java.util.function.Predicate;

public abstract class Dude extends OperableEntityCls implements Killable  {
    private int resourceLimit;
    private int health;
    private int healthLimit;
    public Dude(String id, Point position, int animationPeriod, int actionPeriod, int resourceLimit, List<PImage> image, int healthLimit, int startingHealth ) {
        super(id, position, image, animationPeriod, actionPeriod);
        this.resourceLimit = resourceLimit;
        this.health = startingHealth;
        this.healthLimit = healthLimit;
    }

    public int getResourceLimit() {
        return resourceLimit;
    }

    public void setResourceLimit(int resourceLimit) {
        this.resourceLimit = resourceLimit;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealthLimit() {
        return healthLimit;
    }

    public void setHealthLimit(int healthLimit) {
        this.healthLimit = healthLimit;
    }
}
