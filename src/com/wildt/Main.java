package com.wildt;

import com.googlecode.lanterna.TerminalFacade;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.input.Key;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Terminal terminal = TerminalFacade.createTerminal(System.in, System.out, StandardCharsets.UTF_8);
        boolean loop = true;

        while (loop) {
            printMenu();
            switch (chooseMenuOption()) {
                case 1:
                    gameLoop(terminal);
                    break;
                case 2:
                    printGameInfo();
                    break;
                case 3:
                    loop = false;
                    break;
                default:
                    System.out.println("Invalid option.");

            }
        }
    }

    private static void gameLoop (Terminal terminal) {
        boolean gameLoop = true;

        //Opens the terminal in a new window
        terminal.enterPrivateMode();

        while (gameLoop) {
            runNewGame(terminal);
            if(restartGame(terminal)) {
                //Resets the score each time game is restarted
                GameMap.appleEatenCounter = 0;
            } else {
                gameLoop = false;
                terminal.exitPrivateMode();
            }
        }
    }

    private static boolean restartGame(Terminal terminal) {
        terminal.clearScreen();
        GameMap.printMessage(terminal, GameMap.WIDTH - (GameMap.WIDTH / 2) - 26, GameMap.HEIGHT - (GameMap.HEIGHT / 2),
                "- Press Enter to restart game or any other key to quit -");
        Key key = null;
        while (key == null) {
            key = Movement.keyPress(terminal);
        }
        return key.getKind() == Key.Kind.Enter;
    }

    private static void printMenu() {
        System.out.println("Enter number of the options below:");
        System.out.println("1. Start game.");
        System.out.println("2. Information about the game.");
        System.out.println("3. Quit.");
    }

    private static void printGameInfo() {
        System.out.println("-----------------------------------------------------------------------------");
        System.out.println("This is a snake game created by Anna Wildt for DA556B VT20 at HKR.");
        System.out.println("You move the snake with the arrow keys on your keyboard.");
        System.out.println("If your snake reaches an apple it will grow.");
        System.out.println("If your snake collides with itself or a wall you lose and the score is reset.");
        System.out.println("-----------------------------------------------------------------------------");
    }

    private static int chooseMenuOption() {
        int chosenOption = 0;
        Scanner menuOption = new Scanner(System.in);
        try {
            chosenOption = menuOption.nextInt();
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error invalid value. Try again.");
        }
        return chosenOption;
    }

    private static void runNewGame(Terminal terminal){
        boolean gameOver = false;

        Movement move = new Movement();
        Snake snake = new Snake();

        GameMap.createGameMap(terminal);
        GameMap.drawSnake(terminal, snake.startSnake());
        GameMap.drawApple(terminal, Apple.spawnApple());
        GameMap.printMessage(terminal, GameMap.WIDTH - (GameMap.WIDTH / 2) - 17, GameMap.HEIGHT - (GameMap.HEIGHT / 2) + 1,
                "-Press right or left arrow to start-");

        //Allows the snake to move as long as no game over condition is met
        while (!gameOver) {
            gameOver = move.snakeMovementLoop(terminal, snake);
        }
        gameOver(terminal);
    }

    private static void gameOver(Terminal terminal) {
        //If game over condition is met game over sound is played and returns to start.
        GameMap.printMessage(terminal, GameMap.WIDTH - (GameMap.WIDTH / 2) - 6, GameMap.HEIGHT - (GameMap.HEIGHT / 2), "-GAME OVER-");

        MP3Player sound = new MP3Player();
        sound.play("gameOver.mp3");

        //Thread sleep to play the whole gameOver-sound before restarting the game
        try {
            Thread.sleep(3500);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Thread sleep error.");
        }
    }
}
