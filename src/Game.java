import processing.core.PApplet;

import java.util.Stack;

public class Game{

    private boolean running = false;
    private Stack<GameState> states = new Stack<>();
    private static Game singleton;
    private PApplet screen;

    public Game(){

    }

    public void Init(PApplet screen){
        this.screen = screen;
        running = true;
    }

    public void cleanup() {
        // cleanup the all states
        while ( !states.empty() ) {
            states.peek().Cleanup();
            states.pop();
        }


        System.out.println("GameEngine Cleanup\n");

        // shutdown SDL
        screen.exit();
    }

    public void ChangeState(GameState state) {
        // cleanup the current state
        if ( !states.empty() ) {
            states.peek().Cleanup();
            states.pop();
        }

        // store and init the new state
        states.push(state);
        states.peek().Init(singleton);
    }

    public static Game getSingleton (){
        if(singleton == null){
            singleton = new Game();
        }
        return singleton;
    }

    public PApplet getScreen(){
        return screen;
    }

    public void PushState(GameState state) {
        // pause current state
        if ( !states.empty() ) {
            states.peek().Pause();
        }

        // store and init the new state
        states.push(state);
        states.peek().Init(singleton);

    }

    public void PopState() {
        // cleanup the current state
        if ( !states.empty() ) {
            states.peek().Cleanup();
            states.pop();
        }

        // resume previous state
        if ( !states.empty() ) {
            states.peek().Resume();
        }
    }

    public void handleEvents(UserEvent event) {
        states.peek().HandleEvents(event);
    }

    public void Update() {
        // let the state update the game
        states.peek().Update(this);

    }

    public void Draw() {
        states.peek().Draw(this);
    }

    public boolean running() { return running; }

    public void Quit() { running = false; }

}
