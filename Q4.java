//Heba Alnabahin 220231513
package com.example.demo1.assignment3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Q4 {

    public static void main(String[] args) {
        String filePath = "input.txt";
        try {
            Map<Character, Long> charSummary = Files.lines(Paths.get(filePath))
                    .map(String::toLowerCase)
                    .flatMapToInt(String::chars)
                    .mapToObj(c -> (char) c)
                    .filter(Character::isLetter)
                    .collect(Collectors.groupingBy(
                            Function.identity(),
                            Collectors.counting()
                    ));

            System.out.println("Character Summary (Lambda)");
            charSummary.forEach((character, count) ->
                    System.out.println("'" + character + "'->" + count)
            );

        } catch (IOException e) {
            e.getMessage();
        }
    }
}
