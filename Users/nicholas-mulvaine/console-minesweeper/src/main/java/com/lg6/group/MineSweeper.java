package com.lg6.group;

import java.util.Random;
import java.util.Scanner;

public class MineSweeper {
    private static final int SIZE = 10;
    private static final char UNREVEALED = '#';
    private static final char MINE = '*';
    private static final char CLEARED = ' ';
    private static final char[][] grid = new char[SIZE][SIZE];
    private static final boolean[][] mines = new boolean[SIZE][SIZE];
    private static final Scanner scanner = new Scanner(System.in);
    private static int lives = 3;

    public static void main(String[] args) {
        // Initialize the grid and place mines
        initGrid();
        placeMines(10);  // You can change the number of mines here

        boolean gameOver = false;

        while (!gameOver && lives > 0) {
            printGrid();
            System.out.println("You have " + lives + " lives remaining.");
            String input = getUserInput();
            int[] position = parseInput(input);

            if (position == null) {
                System.out.println("Invalid input! Try again.");
                continue;
            }

            int row = position[0];
            int col = position[1];

            if (mines[row][col]) {
                grid[row][col] = MINE;
                lives--;  // Player loses a life
                System.out.println("You hit a mine! You lost a life. Lives left: " + lives);
                if (lives == 0) {
                    System.out.println("Game over!");
                    gameOver = true;
                }
            } else {
                clearCell(row, col);
                gameOver = checkWin();
            }
        }

        if (gameOver && lives > 0) {
            System.out.println("Congratulations! You cleared the board.");
        } else if (lives == 0) {
            printGrid();  // Print the final state of the grid with all mines shown
            System.out.println("You've run out of lives. Game over.");
        }
    }

    // Initialize the grid with unrevealed cells
    private static void initGrid() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                grid[i][j] = UNREVEALED;
            }
        }
    }

    // Place a set number of mines randomly on the grid
    private static void placeMines(int numMines) {
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced < numMines) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (!mines[row][col]) {
                mines[row][col] = true;
                minesPlaced++;
            }
        }
    }

    // Print the current grid with "System.out.println()" for each line
    private static void printGrid() {
        System.out.println("   ");
        for (int i = 1; i <= SIZE; i++) {
            System.out.print("| " + i + " ");
        }
        System.out.println("|");

        char rowChar = 'A';
        for (int i = 0; i < SIZE; i++) {
            System.out.print(rowChar + " ");
            rowChar++;
            for (int j = 0; j < SIZE; j++) {
                System.out.print("| " + grid[i][j] + " ");
            }
            System.out.println("|");
        }
    }

    // Get user input using "System.out.println()"
    private static String getUserInput() {
        System.out.println("Enter the cell (e.g., A1, B5): ");
        return scanner.nextLine().trim().toUpperCase();
    }

    // Parse the input (e.g., A1) to row and column indices
    private static int[] parseInput(String input) {
        if (input.length() < 2 || input.length() > 3) {
            return null;
        }

        char rowChar = input.charAt(0);
        if (rowChar < 'A' || rowChar > 'J') {
            return null;
        }

        int row = rowChar - 'A';
        int col;

        try {
            col = Integer.parseInt(input.substring(1)) - 1;
            if (col < 0 || col >= SIZE) {
                return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }

        return new int[]{row, col};
    }

    // Clear the cell if it's safe (not a mine)
    private static void clearCell(int row, int col) {
        grid[row][col] = CLEARED;
    }

    // Check if the player has won by clearing all non-mine cells
    private static boolean checkWin() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (!mines[i][j] && grid[i][j] == UNREVEALED) {
                    return false;
                }
            }
        }
        return true;
    }
}