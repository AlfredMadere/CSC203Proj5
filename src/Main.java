import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import processing.core.*;

public final class Main extends PApplet
{

    private Game game;

    public void settings() {
        size(Util.VIEW_WIDTH, Util.VIEW_HEIGHT);
    }

    /*
       Processing entry point for "sketch" setup.
    */
    public void setup() {
        game = Game.getSingleton();
        game.Init(this);
        game.ChangeState(IntroState.getSingleton());

    }

    public void draw(){
        if(game.running()){
            game.Update(mouseX, mouseY);
            game.Draw();
        }else{
            game.cleanup();
        }


    }


    // Just for debugging and for P5
    public void mousePressed() {
        Point mouseLoc = new Point(mouseX, mouseY);
        game.handleEvents(new MouseEvent(mouseLoc, true));



    }

    public void mouseReleased() {
        Point mouseLoc = new Point(mouseX, mouseY);
        game.handleEvents(new MouseEvent(mouseLoc, false));



    }


    public void keyReleased() {
        game.handleEvents(new KeyBoardEvent(key, keyCode, false));




    }

    public void keyPressed() {
        game.handleEvents(new KeyBoardEvent(key, keyCode, true));

    }



    public static void parseCommandLine(String[] args) {
        if (args.length > 1)
        {
            if (args[0].equals("file"))
            {

            }
        }
        for (String arg : args) {
            switch (arg) {
                case Util.FAST_FLAG:
                    Util.timeScale = Math.min(Util.FAST_SCALE, Util.timeScale);
                    break;
                case Util.FASTER_FLAG:
                    Util.timeScale = Math.min(Util.FASTER_SCALE, Util.timeScale);
                    break;
                case Util.FASTEST_FLAG:
                    Util.timeScale = Math.min(Util.FASTEST_SCALE, Util.timeScale);
                    break;
            }
        }
    }

    public static void main(String[] args) {
        parseCommandLine(args);
        PApplet.main(Main.class);
    }
}
