import java.awt.event.KeyListener;

public interface OperableEntity extends SchedulableEntity {
    void executeActivity(
            WorldModel world,
            ImageStore imageStore,
            EventScheduler scheduler);


}
