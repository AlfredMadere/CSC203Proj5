import java.util.*;
import processing.core.PImage;
//Entity functions and actions

/**
 * An this that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */

public final class House extends EntityCls
{

    public House(
            String id,
            Point position,
            PImage image)
    {
        super(id, position, image);
    }


}
