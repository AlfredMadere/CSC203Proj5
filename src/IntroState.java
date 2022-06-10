import processing.core.PApplet;
import processing.core.PImage;

import java.awt.*;
import java.awt.Image.*;
import java.util.ArrayList;
import java.util.List;

import static processing.core.PConstants.CODED;

public class IntroState implements GameState{
    String backgroundImageName = Util.INTRO_IMAGE_NAME;
    private PApplet screen;
    private static GameState singleton;
    private PImage backgroundImage;
    private Game game;
    private List<Button> buttonList = new ArrayList<>();


    private long nextTime;

    public static GameState getSingleton (){
        if(singleton == null){
            singleton = new IntroState();
        }
        return singleton;
    }

    @Override
    public void Init(Game game) {
        this.game = game;
        screen = game.getScreen();
        PImage img = screen.loadImage(backgroundImageName);
        img.resize(Util.VIEW_WIDTH, Util.VIEW_HEIGHT); //not working for some reason?? even though this is what the internet said
        img.updatePixels();
        backgroundImage = img;
        Button startButton = new RoundButton(50, new Color(255, 0, 0), "PLAY", new Point(325, 400));
        startButton.setAction(() -> changeState(GamePlayState.getSingleton()));
        buttonList.add(startButton);


    }


    @Override
    public void Cleanup() {

    }

    @Override
    public void Pause() {

    }

    @Override
    public void Resume() {

    }

    @Override
    public void HandleEvents(UserEvent event) {
        for(Button b : buttonList){
            b.handleUserEvent(event);
        }
    }



    @Override
    public void Draw(Game game) {
        this.screen.image(backgroundImage, 0, 0,
               screen.displayWidth, screen.displayHeight);
        for(Button b : buttonList){
            b.draw(this.screen);
        }
        //this.screen.background(backgroundImage);

    }

    @Override
    public void Update(Game game) {
        for(Button b : buttonList){
            b.update(game.getMouseX(), game.getMouseY());
        }
    }

    @Override
    public void changeState( GameState state) {
        game.ChangeState(state);
    }


}
