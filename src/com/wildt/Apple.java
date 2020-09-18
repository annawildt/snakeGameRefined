package com.wildt;

import java.awt.Point;
import java.util.Random;

public class Apple {
    public static Point applePosition;

    public static Point spawnApple() {
        //Spawns apples at random positions
        Random random = new Random();
        int x = random.nextInt(GameMap.WIDTH - 4) + 2;
        int y = random.nextInt(GameMap.HEIGHT - 2) + 1;
        applePosition = new Point(x, y);

        return applePosition;
    }
}