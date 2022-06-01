import java.util.Objects;

public class aStarNode {
    private int g;
    private int h;
    private int f;
    private aStarNode previous;
    private Point point;
    public aStarNode(Point point, int g, int h, aStarNode prev){
        this.point = point;
        this.g = g;
        this.h = h;
        this.f = this.g + this.h;
        this.previous = prev;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        aStarNode aStarNode = (aStarNode) o;
        return Objects.equals(point, aStarNode.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(point);
    }
    public int getF(){
        return this.f;
    }
    public int getG(){
        return this.g;
    }

    public Point getPoint (){
        return point;
    }

    public aStarNode getPrev() {
        return previous;
    }
    public aStarNode setPrev(aStarNode prev) {
        previous = prev;
        return this;
    }
}
