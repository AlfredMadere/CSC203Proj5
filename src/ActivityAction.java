/**
 * An this that can be taken by an entity
 */
public final class ActivityAction implements Action
{
    private OperableEntity entity;
    private WorldModel world;
    private ImageStore imageStore;

    public ActivityAction(
            OperableEntity entity,
            WorldModel world,
            ImageStore imageStore)
    {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    public void executeAction(EventScheduler scheduler) {
        this.entity.executeActivity(this.world,
                        this.imageStore, scheduler);
    }
}
