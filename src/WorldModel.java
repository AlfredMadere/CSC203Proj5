import processing.core.PImage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Represents the 2D World in which this simulation is running.
 * Keeps track of the size of the world, the background image for each
 * location in the world, and the entities that populate the world.
 */
public final class WorldModel
{
    private int numRows;
    private int numCols;
    private Background background[][];
    private Entity occupancy[][];
    private Set<Entity> entities;
    private Player player;
    private House house;
    private boolean inZombieMode;

    public WorldModel(int numRows, int numCols, Background defaultBackground) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.background = new Background[numRows][numCols];
        this.occupancy = new Entity[numRows][numCols];
        this.entities = new HashSet<>();
        inZombieMode = false;

        setBackground(defaultBackground);
    }

    public void setBackground(Background newBackground){
        for (int row = 0; row < numRows; row++) {
            Arrays.fill(this.background[row], newBackground);
        }
    }

    public Optional<Entity> findNearest(Point pos, List<Class> kinds)
    {
        List<Entity> ofType = new LinkedList<>();
        for (Class kind: kinds)
        {
            for (Entity entity : this.entities) {
                if (entity.getClass() == kind) {
                    ofType.add(entity);
                }
            }
        }

        return nearestEntity(ofType, pos);
    }
    public Optional<Entity> nearestEntity(
            List<Entity> entities, Point pos)
    {
        if (entities.isEmpty()) {
            return Optional.empty();
        }
        else {
            Entity nearest = entities.get(0);
            int nearestDistance = distanceSquared(nearest.getPosition(), pos);

            for (Entity other : entities) {
                int otherDistance = distanceSquared(other.getPosition(), pos);

                if (otherDistance < nearestDistance) {
                    nearest = other;
                    nearestDistance = otherDistance;
                }
            }

            return Optional.of(nearest);
        }
    }

    public void removeEntity(Entity entity) {
        removeEntityAt(entity.getPosition());
    }

    public void removeEntityAt(Point pos) {
        if (withinBounds(pos) && getOccupancyCell(pos) != null) {
            Entity entity = getOccupancyCell(pos);

            /* This moves the entity just outside of the grid for
             * debugging purposes. */
            entity.setPosition(new Point(-1, -1));
            this.entities.remove(entity);
            setOccupancyCell(pos, null);
        }
    }

    public boolean withinBounds(Point pos) {
        return pos.y >= 0 && pos.y < this.numRows && pos.x >= 0
                && pos.x < this.numCols;
    }
    public boolean isOccupied(Point pos) {
        return this.withinBounds(pos) && getOccupancyCell(pos) != null;
    }

    public Entity getOccupancyCell(Point pos) {
        return occupancy[pos.y][pos.x];
    }
    public void setOccupancyCell(Point pos, Entity entity)
    {
        occupancy[pos.y][pos.x] = entity;
    }
    public void tryAddEntity(Entity entity) {
        if (isOccupied(entity.getPosition())) {
            // arguably the wrong type of exception, but we are not
            // defining our own exceptions yet
            throw new IllegalArgumentException("position occupied");
        }
        if(entity instanceof Player){
            if(player != null){
                throw new IllegalArgumentException("cannot have two players");
            }
            this.player = (Player) entity;
        }else if(entity instanceof House){
            if(house != null){
                throw new IllegalArgumentException("cannot have two houses");
            }
            this.house = (House) entity;
        }
        addEntity(entity);
    }
    public void addEntity(Entity entity) {
        if (withinBounds(entity.getPosition())) {
            setOccupancyCell(entity.getPosition(), entity);
            entities.add(entity);
        }
    }
    public Optional<Entity> getOccupant(Point pos) {
        if (isOccupied(pos)) {
            return Optional.of(getOccupancyCell(pos));
        }
        else {
            return Optional.empty();
        }
    }

    public void maybeChangeToZombieMode(Point pressed, EventScheduler scheduler, ImageStore imageStore){
        /*
        change background tiles
        iterate through every type of entity and use a change Images method to change their images
        to the zombie ones

        then create zombies and make family spawn around house
        */
        if(getHouse().getPosition().equals(pressed) && !inZombieMode){
            Background zombieModeBackground = Factory.createBackground(Util.ZOMBIE_BACKGROUND_NAME, imageStore.getImageList(Util.ZOMBIE_BACKGROUND_NAME));
            setBackground(zombieModeBackground);
            //this is gonna be jank because the Util key names are not static members of each class but im rushed for time sooo check this mess out
            List<House> houses = getEntities().stream().filter((e) -> e instanceof House).map(e -> ((House)e)).collect(Collectors.toList());
            //gonna do a little trick here where there are zombies but they look like dudes when the game starts, then they change to have zombie skin
            List<Zombie> zombies = getEntities().stream().filter((e) -> e instanceof Zombie).map(e -> ((Zombie)e)).collect(Collectors.toList());
            List<Tree> trees = getEntities().stream().filter((e) -> e instanceof Tree).map(e -> ((Tree)e)).collect(Collectors.toList());
            //repeat this patern for any other entities that exist when the game starts
            for(House h : houses){
                h.changeImage(imageStore.getImageList(Util.HOUSE_KEY + "Z").get(0));
            }
            for(Zombie z : zombies){
                z.changeImages(imageStore.getImageList(Util.ZOMBIE_KEY + "Z"));
            }
            for(Tree t : trees){
                t.changeImages(imageStore.getImageList(Util.TREE_KEY + "Z"));
            }
//            GamePlayState.loadImages(Util.IMAGE_LIST_FILE_NAME, imageStore, Ggame.getScreen());
//            GamePlayState.loadWorld(world, Util.LOAD_FILE_NAME, imageStore);
            inZombieMode = true;
        }




    }

    public void upgradeHouse(Point pressed, EventScheduler scheduler, ImageStore imageStore){
        if (getHouse().getPosition().equals(pressed) && !getHouse().isMega()){ //changed this to be more dynamic
            House oldHouse = getHouse();
            removeEntity(oldHouse);
            scheduler.unscheduleAllEvents(oldHouse);
            House newHouse = (House) Factory.createMegaHouse("Mansion", new Point(pressed.x - 2, pressed.y - 2), imageStore.getImageList("mansion"), 20);
            addEntity(newHouse);
            setHouse(newHouse);

        }
    }
    public void moveEntity(Entity entity, Point pos) {
        Point oldPos = entity.getPosition();
        if (withinBounds(pos) && !pos.equals(oldPos)) {
            setOccupancyCell(oldPos, null);
            removeEntityAt(pos);
            setOccupancyCell(pos, entity);
            entity.setPosition(pos);
        }
    }

    public Background getBackgroundCell(Point pos) {
        return background[pos.y][pos.x];
    }

    public void setBackgroundCell(
            Point pos, Background background)
    {
        this.background[pos.y][pos.x] = background;
    }

    public void setBackground(
            Point pos, Background background)
    {
        if (withinBounds(pos)) {
            setBackgroundCell( pos, background);
        }
    }

    public Optional<PImage> getBackgroundImage(
             Point pos)
    {
        if (withinBounds(pos)) {
            return Optional.of(getBackgroundCell(pos).getCurrentImage());
        }
        else {
            return Optional.empty();
        }
    }

    public static int distanceSquared(Point p1, Point p2) {
        int deltaX = p1.x - p2.x;
        int deltaY = p1.y - p2.y;

        return deltaX * deltaX + deltaY * deltaY;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public Set<Entity> getEntities() {
        return entities;
    }

    public Player getPlayer() {
        return player;
    }

    public House getHouse() {
        return house;
    }
    public void setHouse(House h) {
        this.house = h;
    }
}
