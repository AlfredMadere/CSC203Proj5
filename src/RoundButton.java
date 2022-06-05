import processing.core.PApplet;
import processing.core.PConstants;

import java.awt.*;

public class RoundButton extends Button{
    private int radius;
    public RoundButton(int radius, Color color, String text, Point p){
        super(text, color, p);
        this.radius = radius;
    }


    @Override
    public boolean _mouseOver(int mouseX, int mouseY) {
        return getPosition().distanceTo(new Point(mouseX, mouseY)) < radius;
    }

    @Override
    void _drawShapeAndText(PApplet screen) {
        screen.circle(getPosition().x, getPosition().y, radius*2);
        screen.fill(0,0,0); //could add text color later for more flexibiliy
        screen.textMode(PConstants.CENTER);
        screen.text(getText(), getPosition().x, getPosition().y);

    }
}
