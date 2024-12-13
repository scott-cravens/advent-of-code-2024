import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

public class Day_07 {

    public static void main(String[] args) {

        // Read and parse the input data
        List<List<Long>> input = readInputData(args[0]).stream()
                .map(line -> Arrays.stream(line.split(":? "))
                .map(Long::parseLong).toList()).toList();

        long resultPart1 = 0, resultPart2 = 0;

        for (List<Long> numbers : input) {
            long target = numbers.get(0);
            if (isValid1(numbers, target, numbers.get(1), 2)) {
                resultPart1 += target;
                resultPart2 += target;
            } else if (isValid2(numbers, target, numbers.get(1), 2)) {
                resultPart2 += target;
            }
        }

        System.out.printf("Part 1: %d\nPart 2: %d\n", resultPart1, resultPart2);
    }

    // Determines if it's possible to reach the target number from the current number
    // by adding and multiplying numbers from the numbers list.
    private static boolean isValid1(List<Long> numbers, long target, long current, int index) {

        // If we've reached the end of the list, check if current equals target.
        if (index == numbers.size()) {
            return current == target;
        }

        // Try adding the next number to the current number.
        long next = current + numbers.get(index);
        if (next <= target && isValid1(numbers, target, next, index + 1)) {
            return true;
        }

        // Try multiplying the next number to the current number.
        long next2 = current * numbers.get(index);
        return next2 <= target && isValid1(numbers, target, next2, index + 1);

        // If none of the above works, return false.
    }

    // Determines if it's possible to reach the target number from the current number
    // by adding, multiplying, or concatenating numbers from the numbers list.
    private static boolean isValid2(List<Long> numbers, long target, long current, int index) {

        // If current number exceeds target, it's not valid.
        if (current > target) return false;

        // If we've reached the end of the list, check if current equals target.
        if (index == numbers.size()) return current == target;

        // Skip processing if the current number is zero.
        if (numbers.get(index) == 0) return isValid2(numbers, target, current, index + 1);

        // Try adding, multiplying, and concatenating the current number.
        return isValid2(numbers, target, current + numbers.get(index), index + 1)
                || isValid2(numbers, target, current * numbers.get(index), index + 1)
                || isValid2(numbers, target, concatenate(current, numbers.get(index)), index + 1);
    }

    // Concatenates two long numbers into a single long number, by parsing the
    // concatenated string into a long.
    private static long concatenate(long num1, long num2) {
        return Long.parseLong(String.valueOf(num1) + num2);
    }

    // Reads the input data from a file given by the resource name.
    private static List<String> readInputData(String resourceName) {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(resourceName).toURI());
            return Files.readAllLines(path);
        } catch (Exception e) {
            throw new RuntimeException(format("Error reading the file: %s%n", e.getMessage()));
        }
    }
}