package com.wildt;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    List<SnakePart> snakeBody;

    //The snake is built of blocks and stored in an arrayList
    public Snake() {
        snakeBody = new ArrayList<>();
    }

    public List<SnakePart> startSnake() {
        //The starting snake has 4 body parts with each body part's coordinate blow the next
        for (int i = 0; i < 4; i++) {
            SnakePart snakePart = new SnakePart(GameMap.WIDTH / 2, (GameMap.HEIGHT / 2 - i));
            addSnakeBodyParts(snakePart);
        }
        return snakeBody;
    }

    private void addSnakeBodyParts(SnakePart snake) {
        //Each part is added at the start of the snake
        snakeBody.add(0, snake);
    }

    public List<SnakePart> getSnakeBody() {
        return snakeBody;
    }

}

class SnakePart {
    //Each snake part is part of the body that contains its coordinates
    public Point point;
    public SnakePart(int x, int y) {
        this.point = new Point(x,y);
    }
}
