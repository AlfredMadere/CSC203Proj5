import processing.core.PApplet;
import processing.core.PImage;

public class LoseState implements GameState{
    String backgroundImageName = Util.INTRO_IMAGE_NAME;
    private PApplet screen;
    private static GameState singleton;
    private PImage backgroundImage;
    private Game game;

    public static GameState getSingleton (){
        if(singleton == null){
            singleton = new LoseState() {
            };
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
    public void Update(Game game) {

    }

    @Override
    public void changeState(GameState state) {

    }

    @Override
    public void HandleEvents(UserEvent event) {
        if(event instanceof MouseEvent) {
            game.ChangeState(IntroState.getSingleton());
        }
    }



    @Override
    public void Draw(Game game) {
        this.screen.image(backgroundImage, 0, 0,
                screen.displayWidth, screen.displayHeight);
        //this.screen.background(backgroundImage);

    }

}
