import processing.core.PImage;

import java.util.List;

public class AnimatedEntityCls extends EntityCls implements AnimatedEntity {
    private int animationPeriod;
    private List<PImage> images; // this as well
    private int imageIndex = 0; // probably need to move this down to animatedEntity maybe also image list? that sees dumb though. could also make getters and setters for image Index


    public AnimatedEntityCls(String id, Point position, List<PImage> images, int animationPeriod) {
        super(id, position, images.get(0));
        this.images = images;
        this.animationPeriod = animationPeriod;
    }
    public int getAnimationPeriod() {
        return this.animationPeriod;
    }

    public PImage getCurrentImage() {
        return images.get(imageIndex);
    }

    public List<PImage> getImages(){
        return images;
    }

    public void nextImage() {
        this.imageIndex = (this.imageIndex + 1) % this.images.size();
    }

    @Override
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent( this,
                Factory.createAnimationAction(this, 0),
                getAnimationPeriod());
    }
}
