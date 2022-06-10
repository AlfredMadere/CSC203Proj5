import processing.core.PApplet;
import processing.core.PImage;

public class LoseState implements GameState{
    String backgroundImageName = Util.LOSE_SCREEN_NAME;
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
        img.resize(Util.VIEW_WIDTH, Util.VIEW_HEIGHT); //not working for some reason?? even though this is what the internet said
        img.updatePixels();
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
        this.screen.image(backgroundImage, 0, 0);
        //this.screen.background(backgroundImage);

    }

}
