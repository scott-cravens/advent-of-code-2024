import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.lang.String.format;

// Advent of Code 2024 Day 4
// Link: <a href="Advent of Code 2024 Day 4">https://adventofcode.com/2024/day/4</a>
public class Day_04 {

    public static void main(String[] args) {

        List<String> input = readInputData();
        int cols = input.size();
        int rows = input.get(0).length();

        // Part 1
        String xmas = "XMAS";
        int resultPart1 = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (input.get(row).charAt(col) == xmas.charAt(0)) { // Current position match?
                    for (Direction direction : Direction.values()) { // Iterate over all directions
                        if (matchPart1(input, row, col, direction, xmas)) { // Check for match
                            resultPart1++;
                        }
                    }
                }
            }
        }
        System.out.printf("Part 1: %d\n", resultPart1);

        // Part 2
        int resultPart2 = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (input.get(row).charAt(col) == 'A') {
                    boolean one = matchPart2(input, row, col, Direction.UPLEFT, Direction.DOWNRIGHT);
                    boolean two = matchPart2(input, row, col, Direction.UPRIGHT, Direction.DOWNLEFT);
                    if (one && two) {
                        resultPart2++;
                    }
                }
            }
        }
        System.out.printf("Part 2: %d\n", resultPart2);
    }

    private static boolean matchPart1(List<String> input, int row, int col, Direction direction, String xmas) {
        int position = 1;
        while (position < xmas.length()) {
            row += direction.row;
            col += direction.col;

            if (row < 0 || row >= input.size()) return false; // out of row boundary
            if (col < 0 || col >= input.get(row).length()) return false; // out of column boundary
            if (input.get(row).charAt(col) != xmas.charAt(position)) return false; // doesn't match
            position++;
        }
        return true;
    }

    private static boolean matchPart2(List<String> input, int row, int col, Direction dir1, Direction dir2) {
        if (input.get(row).charAt(col) != 'A') return false; // Fail fast if not "A"

        // Check first sequence in dir1 and second in dir2
        int newRow1 = row + dir1.row;
        int newCol1 = col + dir1.col;
        int newRow2 = row + dir2.row;
        int newCol2 = col + dir2.col;

        if (newRow1 >= 0 && newRow1 < input.size() && newCol1 >= 0 && newCol1 < input.get(newRow1).length() &&
            newRow2 >= 0 && newRow2 < input.size() && newCol2 >= 0 && newCol2 < input.get(newRow2).length()) {

            char char1 = input.get(newRow1).charAt(newCol1);
            char char2 = input.get(newRow2).charAt(newCol2);

            return (char1 == 'S' && char2 == 'M') || (char1 == 'M' && char2 == 'S');
        }
        return false;
    }

    private enum Direction {
        UP(0, -1),
        UPRIGHT(1, -1),
        RIGHT(1, 0),
        DOWNRIGHT(1, 1),
        DOWN(0, 1),
        DOWNLEFT(-1, 1),
        LEFT(-1, 0),
        UPLEFT(-1, -1);

        final int col;
        final int row;

        Direction(int col, int row) {
            this.col = col;
            this.row = row;
        }
    }

    private static List<String> readInputData() {
        String resourceName = "Day_04_input.txt";
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(resourceName).toURI());
            return Files.readAllLines(path);
        } catch (Exception e) {
            throw new RuntimeException(format("Error reading the file: %s%n", e.getMessage()));
        }
    }
}