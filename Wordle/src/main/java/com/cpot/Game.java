package com.cpot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Game {

    private final int NUM_TRIES = 5;
    private ArrayList<String> guesses;
    private Set<String> alphabetSet;
    private String word;
    private char[] wordGuessArray = new char[]{'*','*','*','*','*'};

    public Game() {
        guesses = new ArrayList<>(NUM_TRIES);
        // Create a HashSet to store lowercase English characters
        alphabetSet = new HashSet<>();

        // Add all lowercase English characters to the HashSet
        for (char c = 'a'; c <= 'z'; c++) {
            alphabetSet.add(String.valueOf(c));
        }

        word = getRandomWord();
    }

    private String getRandomWord() {
        List<String> list = new ArrayList<>();
        File words = new File("./Wordle/src/words.txt");
        try {
            Scanner scanner = new Scanner(words);
            while (scanner.hasNext()) {
                list.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        Random random = new Random();
        int index = random.nextInt(list.size());
        return list.get(index);
    }


    private boolean guessedAlready(char c) {
        return (alphabetSet.contains("(" + c + ")")
                || alphabetSet.contains("[" + c + "]")
                || alphabetSet.contains("-" + c + "-"));
    }

    public void run() {
        System.out.println("__Wordle__");
        int turn = 0;
        Scanner scanner = new Scanner(System.in);
        while (turn < NUM_TRIES) {
            for (String guess : guesses) {
                System.out.println(guess);
            }
            for (int i = 0; i < NUM_TRIES - turn; i++) {
                System.out.println("*".repeat(NUM_TRIES));
            }
            ArrayList<String> arrayList = new ArrayList<>(alphabetSet);
            Collections.sort(arrayList);
            System.out.println(arrayList);
            System.out.println("Enter your guess: ");
            String currentGuess = scanner.nextLine();
            guesses.add(currentGuess);
            System.out.println("You guessed " + guesses.get(turn));
            if (guesses.get(turn).equals(word)) {
                System.out.println("You won!");
            } else {
                System.out.println("Try again");
            }
            for (int i = 0; i < 5; i++) {
                char[] c = currentGuess.toCharArray();
                char[] w = word.toCharArray();
                    if (c[i] == w[i]) {
                        alphabetSet.add("(" + c[i] + ")");
                        guesses.add(guesses.get(turn).replace(String.valueOf(c[i]), "(" + c[i] + ")"));
                        wordGuessArray[i] = w[i];
                    } else if (word.contains(String.valueOf(c[i]))) {
                        if (!guessedAlready(c[i])) {
                            alphabetSet.add("[" + c[i] + "]");
                        }
                    } else {
                        if (!guessedAlready(c[i])) {
                            alphabetSet.add("-" + c[i] + "-");
                        }
                    }

                //alphabetSet.add("[" + String.valueOf(currentGuess.charAt(i)) + "]");
                alphabetSet.remove(String.valueOf(c[i]));
            }
            turn++;
        }
    }

}
