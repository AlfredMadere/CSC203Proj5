import processing.core.PApplet;
import processing.core.PImage;
import java.util.Optional;

public final class WorldView
{
    private PApplet screen;
    private WorldModel world;
    private int tileWidth;
    private int tileHeight;
    private Viewport viewport;

    public WorldView(
            int numRows,
            int numCols,
            PApplet screen,
            WorldModel world,
            int tileWidth,
            int tileHeight)
    {
        this.screen = screen;
        this.world = world;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.viewport = new Viewport(numRows, numCols);
    }

    public void drawViewport() {
        drawBackground();
        drawEntities();
    }

    public void drawEntities() {
        for (Entity entity : this.world.getEntities()) {
            Point pos = entity.getPosition();

            if (viewport.contains(pos)) {
                Point viewPoint = worldToViewport( pos.x, pos.y);
                this.screen.image(entity.getCurrentImage(),
                        viewPoint.x * this.tileWidth,
                        viewPoint.y * this.tileHeight);
            }
        }
    }

    public void drawBackground() {
        for (int row = 0; row < viewport.getNumRows(); row++) {
            for (int col = 0; col < viewport.getNumCols(); col++) {
                Point worldPoint = viewportToWorld( col, row);
                Optional<PImage> image =
                    world.getBackgroundImage(worldPoint);
                if (image.isPresent()) {
                    screen.image(image.get(), col * tileWidth,
                            row * tileHeight);
                }
            }
        }
    }
    public void shiftView( int colDelta, int rowDelta) {
        int newCol = clamp(viewport.getCol() + colDelta, 0,
                world.getNumCols() - viewport.getNumCols());
        int newRow = clamp(viewport.getRow() + rowDelta, 0,
                world.getNumRows() - viewport.getNumRows());

        viewport.shift(newCol, newRow);
    }

    //Probably need to move this to viewport or world model
    public Point viewportToWorld( int col, int row) {
        return new Point(col + viewport.getCol(), row + viewport.getRow());
    }

    //Probably need to move this to viewport or world model43.
    public Point worldToViewport( int col, int row) {
        return new Point(col - viewport.getCol(), row - viewport.getRow());
    }
    public static int clamp(int value, int low, int high) {
        return Math.min(high, Math.max(value, low));
    }
}
