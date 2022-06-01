import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestAStar
{ //make this self referential
    private static final HashMap<Integer, aStarNode> aStarNodes= new HashMap<>(){{
        put(1, new aStarNode(new Point(0,0), 3, 4, null));
        put(2, new aStarNode(new Point(1,0), 3, 4, null));
        put(3, new aStarNode(new Point(2,0), 3, 4, null));
        put(4, new aStarNode(new Point(3,0), 3, 4, null));
        put(5, new aStarNode(new Point(4,0), 3, 4, null));
        put(6, new aStarNode(new Point(4,1), 3, 4, null));
        put(7, new aStarNode(new Point(0,2), 3, 4, null));



    }};

    private static final aStarNode[] nodes = new aStarNode[] {
                aStarNodes.get(0),
            aStarNodes.get(1).setPrev(aStarNodes.get(0)),
            aStarNodes.get(2).setPrev(aStarNodes.get(1)),
            aStarNodes.get(3).setPrev(aStarNodes.get(2)),
            aStarNodes.get(4).setPrev(aStarNodes.get(3)),
            aStarNodes.get(5).setPrev(aStarNodes.get(4)),
            aStarNodes.get(6).setPrev(aStarNodes.get(5)),

    };

    @Test
    public void testPathBuilder()
    {
        List<Point> path = new ArrayList<>();
        path = AStarPathingStrategy.recBuildPath(nodes[6], path);

    }

    @Test
    public void testAStarNoObstacles()
    {
        Point start = new Point(0, 0);
        Point end = new Point(2, 2);
        boolean[][] grid = {
                {true, true, true},
                {true, true, true},
                {true, true, true},
        };
        int shortestDist = 3;
        PathingStrategy pathStrat = new AStarPathingStrategy();
        List<Point> path = pathStrat.computePath(start, end, p -> withinBounds(p, grid) && grid[p.y][p.x], Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);
        System.out.println(path);
        assertEquals(path.size(), shortestDist);
        assertTrue(pointsAreAdjacent(path));
        //get rid of first node
        //reverse list
        //check that length is shortest and that every point is adjacent
    }

    @Test
    public void testAStarImpossiblePath()
    {
        Point start = new Point(0, 0);
        Point end = new Point(2, 2);
        boolean[][] grid = {
                {true, true, true},
                {true, true, false},
                {true, false, true},
        };
        int shortestDist = 0;
        PathingStrategy pathStrat = new AStarPathingStrategy();
        List<Point> path = pathStrat.computePath(start, end, p -> withinBounds(p, grid) && grid[p.y][p.x], Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);
        System.out.println(path);
        assertEquals(path.size(), shortestDist);
        assertTrue(pointsAreAdjacent(path));

        //check that length is shortest and that every point is adjacent
    }

    @Test
    public void testAStarWithObstacles()
    {
        Point start = new Point(0, 0);
        Point end = new Point(2, 2);
        boolean[][] grid = {
                {true, true, false},
                {false, true, true},
                {true, false, true},
        };
        int shortestDist = 3;
        PathingStrategy pathStrat = new AStarPathingStrategy();
        List<Point> path = pathStrat.computePath(start, end, p -> withinBounds(p, grid) && grid[p.y][p.x], Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);
        System.out.println(path);

        assertEquals(path.size(), shortestDist);
        assertTrue(pointsAreAdjacent(path));

    }
    @Test
    public void testBigMap()
    {
        Point start = new Point(0, 0);
        Point end = new Point(7, 7);
        boolean[][] grid = {
                {true, false, false, true, false, true, true, true},
                {true, false, false, true, false, true, true, true},
                {true, true, true, true, false, true, true, true},
                {true, false, false, true, false, true, true, true},
                {true, true, false, true, false, true, true, true},
                {true, false, false, true, false, true, true, true},
                {true, true, false, true, false, true, true, true},
                {true, true, false, true, true, true, true, true},

        };
        int shortestDist = 13;
        PathingStrategy pathStrat = new AStarPathingStrategy();
        List<Point> path = pathStrat.computePath(start, end, p -> withinBounds(p, grid) && grid[p.y][p.x], Point::adjacent, PathingStrategy.CARDINAL_NEIGHBORS);
        System.out.println(path);

        assertEquals(path.size(), shortestDist);
        assertTrue(pointsAreAdjacent(path));

    }
    private static boolean withinBounds(Point p, boolean[][] grid)
    {
        return p.y >= 0 && p.y < grid.length &&
                p.x >= 0 && p.x < grid[0].length;
    }
    private static boolean pointsAreAdjacent(List<Point> path)
    {
        boolean result = true;
        for(int i = 0; i < path.size() - 1; i ++){
            if(!Point.adjacent(path.get(i), path.get(i+1))){
                result = false;
            }
        }
        return result;

    }

}
