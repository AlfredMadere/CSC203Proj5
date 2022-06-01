import java.util.*;
import processing.core.PImage;
//Entity functions and actions

/**
 * An this that exists in the world. See EntityKind for the
 * different kinds of entities that exist.
 */

public interface Entity
{
    PImage getCurrentImage();

    String getId();

    Point getPosition();

    void setPosition(Point position);
}
