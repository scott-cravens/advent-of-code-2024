import objects.Direction;
import objects.Point;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.lang.String.format;

// Advent of Code 2024 Day 6
// Link: <a href="Advent of Code 2024 Day 6">https://adventofcode.com/2024/day/6</a>
public class Day_06 {

    public static void main(String[] args) {
        List<String> input = readInputData(args[0]);
        char[][] map = convertListToMap(input);
        Point guard = findGuard(map);
        Direction dir = Direction.NORTH;  // Since ^ indicates facing up

        int visitedCount = simulatePatrol(map, guard, dir);
        System.out.println("Number of distinct positions visited: " + visitedCount);
    }

    private static List<String> readInputData(String resourceName) {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(resourceName).toURI());
            return Files.readAllLines(path);
        } catch (Exception e) {
            throw new RuntimeException(format("Error reading the file: %s%n", e.getMessage()));
        }
    }

    // Convert the list of strings to a 2D char array
    public static char[][] convertListToMap(List<String> input) {
        // Determine the dimensions of the map
        int maxRowLength = input.stream().mapToInt(String::length).max().orElse(0);
        char[][] map = new char[input.size()][maxRowLength];

        // Populate the map
        for (int i = 0; i < input.size(); i++) {
            String line = input.get(i);
            for (int j = 0; j < line.length(); j++) {
                map[i][j] = line.charAt(j);
            }
            // Fill the rest of the row with spaces if the line is shorter than maxRowLength
            for (int j = line.length(); j < maxRowLength; j++) {
                map[i][j] = ' ';
            }
        }
        return map;
    }

    // Find the starting position of the guard
    private static Point findGuard(char[][] map) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (map[i][j] == '^') {
                    return new Point(j, i);
                }
            }
        }
        throw new IllegalStateException("Guard not found in the map.");
    }

    // Simulate the guard's patrol
    private static int simulatePatrol(char[][] map, Point guard, Direction dir) {
        boolean[][] visited = new boolean[map.length][map[0].length];
        visited[guard.y][guard.x] = true;
        int count = 1;

        while (true) {
            Point next = guard.getNextPosition(dir);
            if (isBlocked(map, next)) { // If the next move is obstructed, turn right
                dir = dir.turnRight();
                continue;
            }

            guard = next;
            if (visited[guard.y][guard.x]) {
                // If we've already visited this spot, we don't count it again but continue the patrol
                visited[guard.y][guard.x] = true;  // Just in case we come back to this spot again in a different direction
            } else {
                visited[guard.y][guard.x] = true;
                count++;
            }

            try {
                if (isBlocked(map, guard.getNextPosition(dir))) {
                    dir = dir.turnRight();
                }
            } catch (IndexOutOfBoundsException e) {
                break;
            }
        }

        return count;
    }

    // Check if the position is valid within the map
    private static boolean isBlocked(char[][] map, Point p) {
        return map[p.y][p.x] == '#';
    }
}

