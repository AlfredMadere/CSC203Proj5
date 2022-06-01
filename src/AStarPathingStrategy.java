import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{

    //sometimes seems to get into an infinite loop
    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        aStarNode currentNode = new aStarNode(start, 0, distFormula(start, end), null);
        List<Point> path = new LinkedList<Point>();
        PriorityQueue<aStarNode> openList = new PriorityQueue<>((n1, n2) -> ((Integer) n1.getF()).compareTo(n2.getF())); //sorted by smallest f value
        HashMap<Point, aStarNode> nodesOnOpenList = new HashMap<>(); // idk if this is useful if i can just remove from the priority queue
        openList.add(currentNode);
        nodesOnOpenList.put(currentNode.getPoint(), currentNode);

        HashSet<Point> closedList = new HashSet<>();
        List<Point> validAdjacentPoints = potentialNeighbors.apply(currentNode.getPoint()).filter(canPassThrough).filter((p) -> !closedList.contains(p)).collect(Collectors.toList());
        while(!withinReach.test(currentNode.getPoint(), end)) {
            currentNode = openList.poll();
            nodesOnOpenList.remove(currentNode.getPoint());
            validAdjacentPoints = potentialNeighbors.apply(currentNode.getPoint()).filter(canPassThrough).filter((p) -> !closedList.contains(p)).collect(Collectors.toList());
            for(Point p : validAdjacentPoints){
                int g = currentNode.getG() + 1;
                int h = distFormula(p, end);
                int f = g + h;
                aStarNode nodeToAdd = new aStarNode(p, g, h, currentNode);
                if(nodesOnOpenList.containsKey(p)){ // check if it contains a node with that point
                    if(g < nodesOnOpenList.get(p).getG()){
                        nodesOnOpenList.put(p, nodeToAdd);
                        openList.remove(nodeToAdd); // because of my equality method only checking if the point is tht same, this will update the f value
                        openList.add(nodeToAdd);
                    }
                }else{
                    nodesOnOpenList.put(p, nodeToAdd);
                    openList.add(nodeToAdd);
                }

            }
            closedList.add(currentNode.getPoint());// move current node to closed list (and take off open list)

            nodesOnOpenList.remove(currentNode.getPoint());
            openList.remove(currentNode);


            if(openList.isEmpty()){
                break;
            }

        }
        if(withinReach.test(currentNode.getPoint(), end)){
            return recBuildPath(currentNode, path);
        }
        return path;
    }
    //figure out how concat arrays to build path
    public static List<Point> recBuildPath(aStarNode curNode, List<Point> path){
        if (curNode.getPrev() == null){
            return path;
        }else{
            path.add(0, curNode.getPoint());
            return recBuildPath(curNode.getPrev(), path);
        }
    }

    public int distFormula (Point a, Point b){
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }
}
