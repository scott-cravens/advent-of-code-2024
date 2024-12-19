import objects.Direction;
import objects.Point;
import java.util.*;

import static utils.FileIO.readInputData;

public class Day_10 {

    public static void main(String[] args) {

        List<String> map = readInputData(args[0]);

        System.out.println("Part 1: " + calculateFinalScore(map));
        System.out.println("Part 2: " + calculateFinalRating(map));
    }

    /**
     * Calculates the final score by finding all trailheads and calculating their respective trail scores.
     * A trailhead is a cell with a value of '0'.
     *
     * @param map a 2D list of characters containing the map
     * @return the total score of all trailheads
     */
    public static int calculateFinalScore(List<String> map) {
        int n = map.size();
        int totalScore = 0;

        // Convert the map to a 2D array of characters for easier access
        char[][] grid = new char[n][n];
        for (int i = 0; i < n; i++) {
            grid[i] = map.get(i).toCharArray();
        }

        // Iterate over each cell in the map
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                if (grid[y][x] == '0') { // A trailhead is a cell with a value of '0'
                    totalScore += calculateTrailheadScore(grid, new Point(x, y), n);
                }
            }
        }

        return totalScore;
    }

    /**
     * Calculates the score of a trailhead by performing a breadth-first search to find all connected cells with
     * values from 1 to 9.
     *
     * @param grid a 2D array of characters containing the map
     * @param start the starting point of the trailhead
     * @param n the size of the map
     * @return the total score of the trailhead
     */
    private static int calculateTrailheadScore(char[][] grid, Point start, int n) {
        Queue<Point> queue = new LinkedList<>();
        Set<Point> visited = new HashSet<>(); // Tracks all visited points for this trailhead
        queue.add(start);
        visited.add(start);

        int score = 0;
        int currentTarget = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            Set<Point> levelVisited = new HashSet<>(); // To avoid revisiting cells in the same BFS level

            for (int i = 0; i < size; i++) {
                Point current = queue.poll();

                for (Direction direction : Direction.values()) {
                    Point next = direction.move(current);

                    // Check if the next position is within bounds and matches the current target
                    if (isValid(next, n) && !visited.contains(next) && !levelVisited.contains(next)
                            && grid[next.y][next.x] == (char) ('0' + currentTarget)) {
                        queue.add(next);
                        levelVisited.add(next);
                        visited.add(next);

                        if (currentTarget == 9) {
                            score++;
                        }
                    }
                }
            }

            currentTarget++;
            if (currentTarget > 9) {
                break; // Stop if we've processed up to 9
            }
        }

        return score;
    }

    /**
     * Calculates the final rating of the map by finding all trailheads and calculating their respective trail ratings.
     * A trailhead is a cell with a value of '0'.
     *
     * @param map a 2D list of characters containing the map
     * @return the total rating of all trailheads
     */
    public static int calculateFinalRating(List<String> map) {
        int n = map.size();
        int totalRating = 0;

        // Convert the map to a 2D array of characters for easier access
        char[][] grid = new char[n][n];
        for (int i = 0; i < n; i++) {
            grid[i] = map.get(i).toCharArray();
        }

        // Iterate over each cell in the map
        for (int y = 0; y < n; y++) {
            for (int x = 0; x < n; x++) {
                if (grid[y][x] == '0') { // A trailhead is a cell with a value of '0'
                    totalRating += calculateTrailheadRating(grid, new Point(x, y), n);
                }
            }
        }

        return totalRating;
    }

    /**
     * Calculates the rating of a trailhead by finding all unique paths to '9' and counting them.
     *
     * @param grid a 2D array of characters containing the map
     * @param start the starting point of the trailhead
     * @param n the size of the map
     * @return the rating of the trailhead
     */
    private static int calculateTrailheadRating(char[][] grid, Point start, int n) {
        Queue<PathNode> queue = new LinkedList<>();
        Set<List<Point>> uniquePaths = new HashSet<>(); // Tracks unique paths to '9'

        // Start the BFS at the trailhead
        queue.add(new PathNode(start, new ArrayList<>()));

        int currentTarget = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                PathNode current = queue.poll();

                for (Direction direction : Direction.values()) {
                    Point next = direction.move(current.point);

                    // Check if the next position is within bounds and matches the current target
                    if (isValid(next, n) && grid[next.y][next.x] == (char) ('0' + currentTarget)) {
                        List<Point> newPath = new ArrayList<>(current.path);
                        newPath.add(next);

                        queue.add(new PathNode(next, newPath));

                        // If the target is '9', add the path to uniquePaths
                        if (currentTarget == 9) {
                            uniquePaths.add(newPath);
                        }
                    }
                }
            }

            currentTarget++;
            if (currentTarget > 9) {
                break; // Stop if we've processed up to 9
            }
        }

        return uniquePaths.size(); // Each unique path to '9' contributes to the rating
    }

    /**
     * Checks if a given point is within the bounds of a grid of size n x n.
     *
     * @param p the point to check
     * @param n the size of the grid
     * @return true if the point is within bounds, false otherwise
     */
    private static boolean isValid(Point p, int n) {
        return p.x >= 0 && p.x < n && p.y >= 0 && p.y < n;
    }

    /**
     * A helper class to store a point and its depth in the BFS tree.
     */
    private static class PathNode {
        Point point;
        List<Point> path;

        PathNode(Point point, List<Point> path) {
            this.point = point;
            this.path = path;
        }
    }
}