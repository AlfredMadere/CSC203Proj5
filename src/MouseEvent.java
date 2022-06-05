public class MouseEvent implements UserEvent{
    private Point location;
    private boolean pressed;
    public MouseEvent(Point mouseLocation, boolean pressed){
        location = mouseLocation;
        this.pressed = pressed;
    }

    public Point getLocation() {
        return location;
    }

    public boolean isPressed() {
        return pressed;
    }
}
