package com.cpot;

public class Main {
    public static void main(String[] args) {

        System.out.println("Hello world!");
        System.out.println(System.getProperty("user.dir"));

        Game game = new Game();
        game.run();
    }
}