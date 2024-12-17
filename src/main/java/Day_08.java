import utils.FileIO;
import org.javatuples.Pair;
import org.javatuples.Triplet;
import utils.Utility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day_08 {

    public static void main(String[] args) {

        // Read the input data and convert it to a 2D char array
        char[][] map = convertListToMap(FileIO.readInputData(args[0]));

        // Find all the antennas on the map with their row and column indices
        List<Triplet<Character, Integer, Integer>> antennas = findAntennas(map);

        // Find potential antinodes
        Set<Pair<Integer, Integer>> antinodesPart1 = findAntinodesPart1(antennas);
        Set<Pair<Integer, Integer>> antinodesPart2 = findAntinodesPart2(antennas);

        // Count the number of valid antinodes that are within the bounds of the map
        System.out.println("Part 1: Number of valid antinodes: " + countAntinodesInMapBounds(antinodesPart1, map));
        System.out.println("Part 2: Number of valid antinodes: " + countAntinodesInMapBounds(antinodesPart2, map));
    }

    /**
     * Counts the number of antinodes that are within the bounds of the given map.
     *
     * @param antinodes A set of Pairs where each Pair contains two integers representing the row and column indices
     *                  of potential antinodes.
     * @param map A 2D char array representing the map. The size of the map can be obtained by getting the length
     *            of the outer array and the length of the inner array.
     * @return The number of antinodes that are within the bounds of the map.
     */
    public static int countAntinodesInMapBounds(Set<Pair<Integer, Integer>> antinodes, char[][] map) {
        int count = 0;
        for (Pair<Integer, Integer> antinode : antinodes) {
            if (antinode.getValue0() >= 0 && antinode.getValue0() < map.length
                    && antinode.getValue1() >= 0 && antinode.getValue1() < map[0].length) {
                count++;
            }
        }
        return count;
    }

    /**
     * Finds potential antinodes given a list of antennas with their respective frequencies and positions.
     * An antinode is a point in space relative to two antennas of the same frequency, calculated as
     * symmetric points with respect to the line segment connecting the antennas.
     *
     * @param antennas A list of Triplets where each Triplet contains a character representing the frequency,
     *                 an integer representing the row, and an integer representing the column of an antenna.
     * @return A set of Pairs where each Pair contains two integers representing the row and column indices
     *         of potential antinodes.
     */
    public static Set<Pair<Integer, Integer>> findAntinodesPart1(List<Triplet<Character, Integer, Integer>> antennas) {
        Set<Pair<Integer, Integer>> antinodes = new HashSet<>();

        for (Triplet<Character, Integer, Integer> sourceAntenna : antennas) {
            char frequency = sourceAntenna.getValue0();

            for (Triplet<Character, Integer, Integer> targetAntenna : antennas) {
                if (targetAntenna.getValue0() == frequency && !sourceAntenna.equals(targetAntenna)) {
                    int rowDiff = targetAntenna.getValue1() - sourceAntenna.getValue1();
                    int colDiff = targetAntenna.getValue2() - sourceAntenna.getValue2();

                    antinodes.add(Pair.with(sourceAntenna.getValue1()-rowDiff, sourceAntenna.getValue2()-colDiff));
                    antinodes.add(Pair.with(targetAntenna.getValue1()+rowDiff, targetAntenna.getValue2()+colDiff));
                }
            }
        }

        return antinodes;
    }

    /**
     * Finds potential antinodes given a list of antennas with their respective frequencies and positions.
     * An antinode is a point in space relative to two antennas of the same frequency, calculated as
     * symmetric points with respect to the line segment connecting the antennas.
     *
     * @param antennas A list of Triplets where each Triplet contains a character representing the frequency,
     *                 an integer representing the row, and an integer representing the column of an antenna.
     * @return A set of Pairs where each Pair contains two integers representing the row and column indices
     *         of potential antinodes.
     */
    private static Set<Pair<Integer, Integer>> findAntinodesPart2(List<Triplet<Character, Integer, Integer>> antennas) {
        Set<Pair<Integer, Integer>> antinodes = new HashSet<>();

        for (Triplet<Character, Integer, Integer> sourceAntenna : antennas) {
            char frequency = sourceAntenna.getValue0();

            for (Triplet<Character, Integer, Integer> targetAntenna : antennas) {
                if (targetAntenna.getValue0() == frequency && !sourceAntenna.equals(targetAntenna)) {
                    int rowDiff = targetAntenna.getValue1() - sourceAntenna.getValue1();
                    int colDiff = targetAntenna.getValue2() - sourceAntenna.getValue2();

                    int newSourceRow = sourceAntenna.getValue1() + rowDiff;
                    int newSourceCol = sourceAntenna.getValue2() + colDiff;
                    int newTargetRow = targetAntenna.getValue1() - rowDiff;
                    int newTargetCol = targetAntenna.getValue2() - colDiff;

                    // Add the antinodes to the set that surpass the bounds of the map
                    for (int i = 0; i < 50; i++) {
                        antinodes.add(Pair.with(newSourceRow, newSourceCol));
                        antinodes.add(Pair.with(newTargetRow, newTargetCol));

                        newSourceRow -= rowDiff;
                        newSourceCol -= colDiff;
                        newTargetRow += rowDiff;
                        newTargetCol += colDiff;
                    }
                }
            }
        }

        return antinodes;
    }

    /**
     * Given a 2D char array map, find all the alphanumeric characters on the map and store them in a
     * list of Triplets. The first element of each Triplet is the alphanumeric character, the second
     * element is the row index of the character, and the third element is the column index of the
     * character.
     * @param map A 2D char array map
     * @return A list of Triplets, each Triplet containing an alphanumeric character and its position
     *         on the map
     */
    private static List<Triplet<Character, Integer, Integer>> findAntennas(char[][] map) {
        List<Triplet<Character, Integer, Integer>> antennas = new ArrayList<>();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                char frequency = map[row][col];
                if (Utility.isAlphanumeric(frequency)) {
                    antennas.add(Triplet.with(frequency, row, col));
                }
            }
        }
        return antennas;
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
}