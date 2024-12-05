import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.String.format;

// Advent of Code 2024 Day 1
// Link: <a href="...">https://adventofcode.com/2024/day/1</a>
public class Day_01 {

    static List<Integer> listA = new ArrayList<>();
    static List<Integer> listB = new ArrayList<>();

    public static void main(String[] args) {

        List<String> lines = readInputData();
        separateLines(lines);
        long totalDistance = calculateTotalDistance();
        System.out.printf("Total distance: %d%n",totalDistance);
        int totalSimilarityScore = calculateTotalSimilarityScore(listA, listB);
        System.out.printf("Total similarity score: %d%n", totalSimilarityScore);
    }

    public static int calculateTotalSimilarityScore(List<Integer> listA, List<Integer> listB) {
        int totalSimilarityScore = 0;
        for (int num : listA) {
            int count = countOccurrences(listB, num);
            totalSimilarityScore += num * count;
        }
        return totalSimilarityScore;
    }

    // Count the number of times a number occurs in a sorted list.
    public static int countOccurrences(List<Integer> sortedList, int num) {
        if (sortedList == null || sortedList.isEmpty()) {
            return 0;
        }

        int firstIndex = findBoundary(sortedList, num, true);
        if (firstIndex == -1) {
            return 0; // Number not found
        }

        int lastIndex = findBoundary(sortedList, num, false);
        return lastIndex - firstIndex + 1;
    }

    // Searches for the boundary in a sorted list for a given number.
    private static int findBoundary(List<Integer> sortedList, int num, boolean findFirst) {
        int low = 0;
        int high = sortedList.size() - 1;
        int result = -1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (sortedList.get(mid) == num) {
                result = mid;
                if (findFirst) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            } else if (sortedList.get(mid) < num) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return result;
    }

    private static long calculateTotalDistance() {
        long distance = 0L;
        for (int i = 0; i < listA.size(); i++) {
            distance += absDiffAsLong(listA.get(i), listB.get(i));
        }
        return distance;
    }

    static long absDiffAsLong(int num1, int num2) {
        return Math.abs((long) num1 - num2);
    }

    private static void separateLines(List<String> lines) {

        for (String line : lines) {
            String[] numbers = line.split("\\s+"); // split line by one or more spaces
            int a = Integer.parseInt(numbers[0]);
            int b = Integer.parseInt(numbers[1]);

            listA.add(a);
            listB.add(b);
        }

        // sort the lists
        Collections.sort(listA);
        Collections.sort(listB);

    }

    private static List<String> readInputData() {
        String resourceName = "Day_01_input.txt";
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(resourceName).toURI());
            return Files.readAllLines(path);
        } catch (Exception e) {
            throw new RuntimeException(format("Error reading the file: %s%n", e.getMessage()));
        }
    }
}