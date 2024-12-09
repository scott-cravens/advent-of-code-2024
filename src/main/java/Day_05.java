import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static java.lang.String.format;

public class Day_05 {
    static List<List<Integer>> rules = new ArrayList<>();
    static List<List<Integer>> updates = new ArrayList<>();

    private static void part1() {
        int resultSum = 0;
        for (List<Integer> update : updates) {
            boolean isUpdateValid = true;
            for (List<Integer> rule : rules) {

                // Ignore if all pages in the rule don't exist in the update
                if (!new HashSet<>(update).containsAll(rule)) {
                    continue; // Ignore this rule and move to the next one
                }

                // Check if the pages in the update are in the correct order
                if (!isRuleValid(update, rule)) {
                    isUpdateValid = false;
                    break; // Exit the loop if the rule is not valid
                }
            }

            // If the update is valid, find the middle page
            if (isUpdateValid) {
                int middlePage = update.get(update.size() / 2); // Find the middle page
                resultSum += middlePage;
            }
        }

        System.out.printf("Part 1: %d\n", resultSum);
    }

    private static void part2() {
        int resultSum = 0;
        for (List<Integer> update : updates) {
            boolean isUpdateValid = true;
            for (List<Integer> rule : rules) {

                // Ignore if all pages in the rule don't exist in the update
                if (!new HashSet<>(update).containsAll(rule)) {
                    continue; // Ignore this rule and move to the next one
                }

                // Check if the pages in the update are in the correct order
                if (!isRuleValid(update, rule)) {
                    // Reorder the update according to the rule
                    update = reorderUpdate(update, rule);
                }
            }

            // If the update is valid, find the middle page
            int middlePage = update.get(update.size() / 2); // Find the middle page
            resultSum += middlePage;

        }

        System.out.printf("Part 2: %d\n", resultSum);
    }

    private static List<Integer> reorderUpdate(List<Integer> update, List<Integer> rule) {
        List<Integer> reorderedUpdate = new ArrayList<>(update);
        int index1 = reorderedUpdate.indexOf(rule.get(0));
        int index2 = reorderedUpdate.indexOf(rule.get(1));

        if (index1 != -1 && index2 != -1) {
            int temp = reorderedUpdate.get(index1);
            reorderedUpdate.set(index1, reorderedUpdate.get(index2));
            reorderedUpdate.set(index2, temp);
        }

        return reorderedUpdate;
    }

    private static boolean isRuleValid(List<Integer> update, List<Integer> rule) {
        int ruleIndex = 0;

        for (Integer updatePage : update) {
            if (ruleIndex < rule.size() && updatePage.equals(rule.get(ruleIndex))) {
                ruleIndex++;
            }
        }

        // If we've matched all elements in rule, ruleIndex will be equal to rule.size()
        return ruleIndex == rule.size();
    }

    public static void main(String[] args) {
        List<String> input = readInputData(args[0]);
        convertToLists(input);

        part1();

    }

    private static void convertToLists(List<String> list) {
        List<String> strRules = new ArrayList<>();
        List<String> strUpdates = new ArrayList<>();

        // Split the input into strRules and strUpdates
        for (String line : list) {
            if (line.isEmpty()) {
                strUpdates = list.subList(list.indexOf(line) + 1, list.size());
                break;
            }
            strRules.add(line);
        }

        // Convert the string lists to lists of integers
        rules = convertToIntListOfList(strRules, "\\|");
        updates = convertToIntListOfList(strUpdates, ",");
    }

    private static List<List<Integer>> convertToIntListOfList(List<String> strList, String delimiter) {
        List<List<Integer>> intList = new ArrayList<>();

        for (String s : strList) {
            String[] parts = s.split(delimiter); // Split the s using the provided delimiter
            List<Integer> page = new ArrayList<>();
            for (String part : parts) {
                page.add(Integer.parseInt(part)); // Parse each number and add to the strList
            }
            intList.add(page); // Add the strList of integers to the outer strList
        }

        return intList;
    }

    private static List<String> readInputData(String resourceName) {
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(resourceName).toURI());
            return Files.readAllLines(path);
        } catch (Exception e) {
            throw new RuntimeException(format("Error reading the file: %s%n", e.getMessage()));
        }
    }
}