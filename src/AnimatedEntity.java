import processing.core.PImage;

import java.util.List;

public interface AnimatedEntity extends SchedulableEntity {

    int getAnimationPeriod();
    void nextImage();

}
