import utils.FileIO;

import java.util.Arrays;
import java.util.List;

public class Day_09 {

    public static void main(String[] args) {

        List<String> input = FileIO.readInputData(args[0]);
        int[] blockMap1 = buildBlockMap(input.getFirst());
        int[] blockMap2 = blockMap1.clone();

        // Part 1
        int[] defragmentedBlockMap = defragBlockMap1(blockMap1);
        long checksum = calculateChecksum(defragmentedBlockMap);
        System.out.println("Part 1: " + checksum);

        // Part 2
        defragmentedBlockMap = defragBlockMap2(blockMap2);
        checksum = calculateChecksum(defragmentedBlockMap);
        System.out.println("Part 2: " + checksum);

    }

    /**
     * Calculates the checksum of the given defragmented block map.
     * The checksum is defined as the sum of the products of each index and the value at that index.
     *
     * @param defragmentedBlockMap a defragmented block map where empty spaces are marked with -1
     * @return the checksum of the given block map
     */
    private static long calculateChecksum(int[] defragmentedBlockMap) {
        long checksum = 0;
        // Iterate over the block map and calculate the checksum
        for (int i = 0; i < defragmentedBlockMap.length; i++) {
            if (defragmentedBlockMap[i] != -1) {
                checksum += (long) i * defragmentedBlockMap[i];
            }
        }
        return checksum;
    }

    /**
     * Defragments the given block map by swapping the first available empty space with the first available
     * non-empty space from the right.
     *
     * @param blockMap a block map where empty spaces are marked with -1
     * @return the defragmented block map
     */
    private static int[] defragBlockMap1(int[] blockMap) {
        int leftIndex = 0;
        int rightIndex = blockMap.length - 1;

        while (leftIndex < rightIndex) {
            // Find the first available empty space from the left
            while (blockMap[leftIndex] != -1) {
                leftIndex++;
            }
            // Find the first available non-empty space from the right
            while (blockMap[rightIndex] == -1) {
                rightIndex--;
            }
            // If the spaces are not adjacent, swap them
            if (leftIndex < rightIndex) {
                int temp = blockMap[leftIndex];
                blockMap[leftIndex] = blockMap[rightIndex];
                blockMap[rightIndex] = temp;
            }
        }
        return blockMap;
    }

    private static int[] defragBlockMap2(int[] blockMap) {
        int leftIndex = 0;
        int rightIndex = blockMap.length - 1;
        int blocksInFile = 0;

        // Set rightIndex to the last file in blockMap
        while (blockMap[rightIndex] == -1) {
            rightIndex--;
        }
        int maxFileIdNumber = blockMap[rightIndex];

        for (int fileIdNumber = maxFileIdNumber; fileIdNumber >= 0; fileIdNumber--) {

            System.out.println("Blockmap: " + Arrays.toString(blockMap));

            // Find the number of blocks for the fileIdNumber
            int tmpRightIndex = rightIndex;
            while (blockMap[tmpRightIndex] == maxFileIdNumber) {
                blocksInFile++;
                tmpRightIndex--;
            }

            // Find the next available empty space from the left
            boolean foundEmptySpace = false;
            while (!foundEmptySpace) {

                // Find the next empty space from the left
                while (blockMap[leftIndex] != -1) {
                    leftIndex++;
                }

                // Check to see if the empty space is big enough to hold the fileIdNumber
                foundEmptySpace = true;
                for (int i = 0; i < blocksInFile; i++) {
                    if (blockMap[leftIndex + i] != -1) {
                        foundEmptySpace = false;
                        break;
                    }
                }

                // If empty space is big enough, swap it with the fileIdNumber
                if (foundEmptySpace) {
                    for (int i = 0; i < blocksInFile; i++) {

                        int temp = blockMap[leftIndex];
                        blockMap[leftIndex] = blockMap[rightIndex];
                        blockMap[rightIndex] = temp;
                        leftIndex++;
                        rightIndex--;

                    }
                }
            }
        }



        return blockMap;
    }

    /**
     * Builds a block map based on the given input string.
     * The block map is a sparse array where each element represents a block of data.
     * The elements of the array are either the fileIdNumber of the block or -1 to
     * indicate that the block is empty.
     *
     * @param input a string representing the disk map
     * @return the block map
     */
    private static int[] buildBlockMap(String input) {
        int[] blockMap = new int[calculateMapLength(input)];
        int fileIdNumber = 0;
        int blockMapIndex = 0;

        // iterate over the input string
        for (int i = 0; i < input.length(); i++) {
            int length = Character.getNumericValue(input.charAt(i));

            // iterate over each block of data
            for (int j = 0; j < length; j++) {
                // if the block is on an even index, write the fileIdNumber
                if (i % 2 == 0) {
                    blockMap[blockMapIndex] = fileIdNumber;
                } else { // if the block is on an odd index, write -1 to represent free space
                    blockMap[blockMapIndex] = -1;
                }
                blockMapIndex++;
            }

            // if the block is on an even index, increment the fileIdNumber
            if (i % 2 == 0) {
                fileIdNumber++;
            }
        }
        return blockMap;
    }

    /**
     * Calculates the length of the block map needed to store the given input string.
     * The length of the block map is the sum of the values of the characters in the
     * input string, since each character represents the number of blocks of data.
     *
     * @param input a string representing the disk map
     * @return the length of the block map
     */
    private static int calculateMapLength(String input) {
        int length = 0;
        // iterate over the input string
        for (int i = 0; i < input.length(); i++) {
            // add the value of each character to the length
            length += Character.getNumericValue(input.charAt(i));
        }
        return length;
    }
}