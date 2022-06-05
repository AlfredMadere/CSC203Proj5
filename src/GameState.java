public interface GameState {
    void Init(Game game);
    void Cleanup();

    void Pause();
    void Resume();

    void HandleEvents(UserEvent event);
    void Update(Game game);
    void Draw(Game game);

    void changeState(GameState state);
}
