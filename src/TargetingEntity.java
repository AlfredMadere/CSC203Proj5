import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class TargetingEntity extends OperableEntityCls{

    public TargetingEntity(String id, Point position, List<PImage> image, int animationPeriod, int actionPeriod) {
        super(id, position, image, animationPeriod, actionPeriod);
    }

    public abstract Point _nextPosition( WorldModel world, Point destPos);
    public abstract void _doAdjacency(WorldModel world,
                                      Entity target,
                                      EventScheduler scheduler);

    public boolean moveTo(
            WorldModel world,
            Entity target,
            EventScheduler scheduler)
    {
        if (Point.adjacent(this.getPosition(), target.getPosition())) {
            //helper method that returns what to do if they are adjacent
            _doAdjacency(world, target, scheduler);
            return true;
        }
        else {
            Point nextPos = this._nextPosition(world, target.getPosition());

            if (!this.getPosition().equals(nextPos)) {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }


}
