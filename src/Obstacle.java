import java.util.*;
import processing.core.PImage;
//Entity functions and actions

/**
 * An this that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */

//Need Entity, AnimatedEntity, and CapableEntity
public final class Obstacle extends AnimatedEntityCls
{


    public Obstacle(
            String id,
            Point position,
            List<PImage> images,
            int animationPeriod)
    {
        super(id, position, images, animationPeriod);


    }


}
