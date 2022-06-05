import org.junit.internal.runners.statements.RunAfters;
import processing.core.PApplet;

import java.awt.*;
import java.util.function.Consumer;

public abstract class Button {
    private boolean mouseOver;
    private String text;
    private Color color;
    private Point position;
    private boolean pressed = false;
    private Runnable action;
    public Button(String text, Color color, Point p){
        this.text = text;
        this.color = color;
        this.position = p;

    }
    public void setAction(Runnable action){
        this.action = action;
    }

    public void doAction(){
        action.run();
    }

    abstract void _drawShapeAndText(PApplet screen);
    abstract boolean _mouseOver(int mouseX, int mouseY);

    public void update(int mouseX, int mouseY){
        boolean before = mouseOver;
        mouseOver = _mouseOver(mouseX, mouseY);
        boolean after = mouseOver;
//        if(before != after){
//            System.out.println(mouseOver);
//        }

    }

    public void handleUserEvent(UserEvent e){
        if(e instanceof MouseEvent && mouseOver ){
            MouseEvent mE = (MouseEvent) e;
            if(mE.isPressed()){
                pressed = true;
            }else{

                pressed = false;
                doAction();
            }
        }
    }
    public void draw(PApplet screen){
        screen.fill(color.getRGB());
        if(mouseOver){
            screen.tint(100);
        }if(pressed){
            screen.tint(150);
        }
        _drawShapeAndText(screen);
        screen.noTint();
        screen.noFill();
    }

    public String getText() {
        return text;
    }

    public Color getColor() {
        return color;
    }

    public Point getPosition() {
        return position;
    }
}
