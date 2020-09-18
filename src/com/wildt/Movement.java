package com.wildt;

import com.googlecode.lanterna.input.Key;
import com.googlecode.lanterna.terminal.Terminal;

import java.awt.Point;

public class Movement {
    private boolean keyUp = false;
    private boolean keyDown = false;
    private boolean keyRight = false;
    private boolean keyLeft = false;
    private boolean isCollision = false;
    private int counterForSnakeUpdate = 0;

    public boolean snakeMovementLoop(Terminal terminal, Snake snake) {
        //Method that keeps running until game over condition is met
        while (!isCollision) {
            Key input = keyPress(terminal);
            updateDirectionStatus(input);
            boolean isAllowedToUpdate = snakeUpdateDelay();
            if (isAllowedToUpdate) {
                isCollision = moveSnakeInDirection(terminal, snake);
            }
        }
        return isCollision;
    }

    private boolean moveSnakeInDirection(Terminal terminal, Snake snake) {
        Point head = snake.getSnakeBody().get(0).point;
        Point newPos;

        if (keyUp) {
            newPos = new Point(head.x, head.y - 1);
        } else if (keyDown) {
            newPos = new Point(head.x, head.y + 1);
        } else if (keyRight) {
            newPos = new Point(head.x + 1, head.y);
        } else if (keyLeft) {
            newPos = new Point(head.x - 1, head.y);
        }
        // start of game, not moving in any direction
        else {
            return false;
        }

        //Checks collision that is a game over condition
        isCollision = moveSnakeAndCheckCollision(newPos, snake);
        GameMap.updateGameMap(terminal, snake);

        return isCollision;
    }

    private boolean moveSnakeAndCheckCollision(Point newPos, Snake snake) {
        //"Moves" the snake by adding a new snake part at the new position sent to method
        SnakePart snakePart = new SnakePart(newPos.x, newPos.y);
        snake.snakeBody.add(0, snakePart);

        Collision col = new Collision();

        //If there's no apple collision, move normally by adding new head and removing last body part.
        boolean isAppleFound = col.collisionApple(snake);
        if (!isAppleFound) {
            snake.snakeBody.remove(snake.snakeBody.size() - 1);
        }

        //Returns true if either game over conditions are met
        return col.collisionBody(snake) || col.collisionWall(snake);
    }

    private boolean snakeUpdateDelay() {
        //Method for setting the automated movement speed for the snake
        int SNAKE_UPDATE_THRESHOLD = 12;
        if (counterForSnakeUpdate == SNAKE_UPDATE_THRESHOLD) {
            counterForSnakeUpdate = 0;
            return true;
        }
        counterForSnakeUpdate++;
        return false;
    }

    private void updateDirectionStatus(Key key) {
        //Checks the latest pressed key for the snake's movement
        if (key != null) {
            switch (key.getKind()) {
                case ArrowDown:
                    keyUp = false;
                    keyDown = true;
                    keyRight = false;
                    keyLeft = false;
                    break;
                case ArrowUp:
                    keyUp = true;
                    keyDown = false;
                    keyRight = false;
                    keyLeft = false;
                    break;
                case ArrowRight:
                    keyUp = false;
                    keyDown = false;
                    keyRight = true;
                    keyLeft = false;
                    break;
                case ArrowLeft:
                    keyUp = false;
                    keyDown = false;
                    keyRight = false;
                    keyLeft = true;
                    break;
            }
        }
    }

    public static Key keyPress(Terminal terminal) {
        //Method to check what keyboard key is pressed.
        Key key = null;
        key = terminal.readInput();
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return key;
    }
}


