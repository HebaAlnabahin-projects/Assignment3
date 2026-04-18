//Heba Alnabahin 220231513
package com.example.demo1.assignment3;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class Q3 {

    public static void main(String[] args) {
        try {
            File file = new File("input.txt");
            Scanner scanner = new Scanner(file);

            StringBuilder contentBuilder = new StringBuilder();
            while (scanner.hasNextLine()) {
                contentBuilder.append(scanner.nextLine()).append(" ");
            }
            String fullText = contentBuilder.toString().trim();
            scanner.close();

            System.out.println("Input Text");
            System.out.println(fullText + "\n");

            Map<String, Integer> wordCounts = new HashMap<>();

            String[] words = fullText.toLowerCase().split("\\s+");

            for (String word : words) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                if (!word.isEmpty()) {
                    wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                }
            }

            System.out.println("Word Frequencies");
            wordCounts.forEach((k, v) -> System.out.println(k + ": " + v));

            Map<Character, Integer> letterCounts = new HashMap<>();
            char[] characters = fullText.toLowerCase().toCharArray();

            for (char c : characters) {
                if (Character.isLetter(c)) {
                    letterCounts.put(c, letterCounts.getOrDefault(c, 0) + 1);
                }
            }

            System.out.println("\nLetter Frequencies");
            letterCounts.forEach((k, v) -> System.out.println(k + ": " + v));

        } catch (FileNotFoundException e) {
            System.out.println("Error: The file 'input.txt' was not found.");
        }
    }

}
