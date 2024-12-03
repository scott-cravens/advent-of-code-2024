package day_02;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class Day_02 {

    private static final List<List<Integer>> reports = new ArrayList<>();

    public static void main(String[] args) {

        List<String> lines = readInputData();
        parseInputData(lines);
        determineSafeness_PartA();
        determineSafeness_PartB();
    }

    private static void determineSafeness_PartA() {
        int totalSafeReports = 0;
        for (List<Integer> report : reports) {
            if (isSafe(report)) totalSafeReports++;
        }
        System.out.println(totalSafeReports);
    }

    private static void determineSafeness_PartB() {
        int totalSafeReports = 0;
        for (List<Integer> report : reports) {
            if (isSafePreProcessor(report)) totalSafeReports++;
        }
        System.out.println(totalSafeReports);
    }

    private static boolean isSafePreProcessor(List<Integer> report) {
        if (isSafe(report)) return true;

        for (int skipIndex = 0; skipIndex < report.size(); skipIndex++) {
            List<Integer> newReport = new ArrayList<>(report);
            newReport.remove(skipIndex);
            if (isSafe(newReport)) return true;
        }
        return false;
    }

    private static boolean isSafe(List<Integer> report) {

        if (report == null || report.size() < 2) return true;
        boolean ascending = true;
        boolean descending = true;

        for (int i = 1; i < report.size(); i++) {
            int diff = Math.abs(report.get(i) - report.get(i - 1));
            if (diff > 3) return false;
            if (report.get(i) <= report.get(i - 1)) ascending = false;
            if (report.get(i) >= report.get(i - 1)) descending = false;
        }
        return ascending || descending;
    }

    private static void parseInputData(List<String> lines) {
        for (String report : lines) {
            String[] levels = report.split("\\s+");
            List<Integer> reportList = new ArrayList<>();
            for (String number : levels) {
                reportList.add(Integer.parseInt(number));
            }
            reports.add(reportList);
        }
    }

    private static List<String> readInputData() {
        String resourceName = "Day_02_input.txt";
        try {
            Path path = Paths.get(ClassLoader.getSystemResource(resourceName).toURI());
            return Files.readAllLines(path);
        } catch (Exception e) {
            throw new RuntimeException(format("Error reading the file: %s%n", e.getMessage()));
        }
    }
}