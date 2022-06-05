import processing.core.PApplet;
import processing.core.PImage;

import static processing.core.PConstants.CODED;

public class IntroState implements GameState{
    String backgroundImageName = Util.INTRO_IMAGE_NAME;
    private PApplet screen;
    private static GameState singleton;
    private PImage backgroundImage;
    private Game game;


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
        img.resize(Util.VIEW_WIDTH, Util.VIEW_HEIGHT);// figure out how to resize
        backgroundImage = img;

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
        if(event instanceof MouseEvent) {
            changeState(GamePlayState.getSingleton());
        }
    }

    @Override
    public void Update(Game game) {

    }

    @Override
    public void Draw(Game game) {
        this.screen.image(backgroundImage, 0, 0,
               screen.displayWidth, screen.displayHeight);
        //this.screen.background(backgroundImage);

    }

    @Override
    public void changeState( GameState state) {
        game.ChangeState(state);
    }


}
