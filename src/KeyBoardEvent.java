import processing.core.PApplet;
import processing.core.PConstants;

public class KeyBoardEvent implements UserEvent{
    private char key;
    private int keyCode;
    private boolean pressed;
    public KeyBoardEvent(char key, int keyCode, boolean pressed){
        this.key = key;
        this.keyCode = keyCode;
        this.pressed = pressed;
    }

    public char getKey() {
        return key;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public boolean isPressed() {
        return pressed;
    }
}
