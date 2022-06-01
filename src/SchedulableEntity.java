public interface SchedulableEntity extends Entity{
    void scheduleActions(
            EventScheduler scheduler,
            WorldModel world,
            ImageStore imageStore);
}
