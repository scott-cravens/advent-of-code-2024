import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

// Advent of Code 2024 Day 3
// Link: <a href="...">https://adventofcode.com/2024/day/3</a>
public class Day_03 {

    public static void main(String[] args) {
        List<String> input = readInputData();
        final Pattern pattern = Pattern.compile("mul\\((\\d+),(\\d+)\\)|(do|don't)\\(\\)");

        boolean inDoBlock = true;
        int resultPart1 = 0, resultPart2 = 0;
        for (String s : input) {
            Matcher matcher = pattern.matcher(s);
            while (matcher.find()) {
                if (matcher.group(0).startsWith("mul")) {
                    int product = Integer.parseInt(matcher.group(1)) * Integer.parseInt(matcher.group(2));
                    resultPart1 += product;
                    if (inDoBlock) resultPart2 += product;
                } else {
                    inDoBlock = !matcher.group(0).startsWith("don't");
                }
            }
        }

        System.out.printf("Part 1: %d\n", resultPart1);
        System.out.printf("Part 2: %d\n", resultPart2);
    }

    private static List<String> readInputData() {
        String resourceName = "Day_03_input.txt";
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(resourceName).toURI());
            return Files.readAllLines(path);
        } catch (Exception e) {
            throw new RuntimeException(format("Error reading the file: %s%n", e.getMessage()));
        }
    }
}
