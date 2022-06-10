import processing.core.PImage;

import java.util.List;

public abstract class EntityCls implements Entity{
    private String id;
    private Point position;
    private PImage image;

    public EntityCls(String id, Point position, PImage image) {
        this.id = id;
        this.position = position;
        this.image = image;
    }


    public void changeImage(PImage image){
        this.image = image;
    }
    public String getId() {
        return id;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
    public PImage getCurrentImage() {
        return image;
    }

}
