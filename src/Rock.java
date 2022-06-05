import processing.core.PImage;

public class Rock extends EntityCls implements Killable{
    private int health;

    public Rock(String id, Point position, PImage image, int health) {
        super(id, position, image);
        this.health = health;
    }

    public int getHealth() {return health;}

    public void setHealth(int h) {this.health = h;}
}
