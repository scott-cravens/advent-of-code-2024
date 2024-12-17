package utils;

public class Utility {

    public static boolean isAlphanumeric(char character) {
        return (character >= 'A' && character <= 'Z') ||
                (character >= 'a' && character <= 'z') ||
                (character >= '0' && character <= '9');
    }
}
