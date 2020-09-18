package com.wildt;

import com.googlecode.lanterna.terminal.Terminal;

import java.util.List;
import java.awt.Point;

public class GameMap {
    public static final int WIDTH = 99;
    public static final int HEIGHT = 29;

    public static int appleEatenCounter = 0;

    public static void createGameMap(Terminal terminal) {
        //Always start fresh
        terminal.clearScreen();

        //Draws ip the borders of the map
        drawBorders(terminal);

        //Draws the current score. 10  points for each apple
        printMessage(terminal, 80, 29, "Score: " + (appleEatenCounter * 10));

        //Removes the cursor that appears where something was drawn last
        terminal.setCursorVisible(false);
    }

    private static void drawBorders(Terminal terminal) {
        int[] borderVertical = new int[]{0, WIDTH};
        int[] borderVertical2 = new int[]{1, WIDTH-1};
        int[] borderHorizontal = new int[]{0, HEIGHT};

        terminal.applyBackgroundColor(Terminal.Color.WHITE);

        for (int v : borderVertical) {
            for (int i = 0; i <= HEIGHT; i++) {
                terminal.moveCursor(v, i);
                terminal.putCharacter(' ');
            }
        }
        for (int v : borderVertical2) {
            for (int i = 0; i <= HEIGHT; i++) {
                terminal.moveCursor(v, i);
                terminal.putCharacter(' ');
            }
        }
        for (int h : borderHorizontal) {
            for (int i = 0; i <= WIDTH; i++) {
                terminal.moveCursor(i, h);
                terminal.putCharacter(' ');
            }
        }
    }

    public static void updateGameMap(Terminal terminal, Snake snake) {
        //Redraws the game map each time this methods run with the positions of the objects.
        createGameMap(terminal);
        drawSnake(terminal, snake.getSnakeBody());
        drawApple(terminal, Apple.applePosition);
    }

    public static void drawSnake(Terminal terminal, List<SnakePart> snakeList) {
        //Snake is drawn with ↂ as marker of each body part and the background green to visualize the whole snake
        Point head = snakeList.get(0).point;
        terminal.moveCursor(head.x, head.y);
        terminal.applyForegroundColor(Terminal.Color.BLACK);
        terminal.applyBackgroundColor(Terminal.Color.GREEN);
        terminal.putCharacter('\u2182');

        for (int i = 1; i < snakeList.size(); i++) {
            Point point = snakeList.get(i).point;
            terminal.moveCursor(point.x, point.y);
            terminal.applyForegroundColor(Terminal.Color.BLACK);
            terminal.applyBackgroundColor(Terminal.Color.GREEN);
            terminal.putCharacter('\u2182');
        }
    }

    public static void drawApple(Terminal terminal, Point applePosition) {
        //Draws a red apple visualised by this symbol ⬤
        terminal.moveCursor(applePosition.x, applePosition.y);
        terminal.applyBackgroundColor(Terminal.Color.DEFAULT);
        terminal.applyForegroundColor(Terminal.Color.RED);
        terminal.putCharacter('\u2B24');
    }

    public static void printMessage(Terminal terminal, int x, int y, String message) {
        for (int i = 0; i < message.length(); i++) {
            terminal.applyBackgroundColor(Terminal.Color.WHITE);
            terminal.applyForegroundColor(Terminal.Color.BLACK);
            terminal.moveCursor(x, y);
            terminal.putCharacter(message.charAt(i));
            x++;
        }
    }
}
