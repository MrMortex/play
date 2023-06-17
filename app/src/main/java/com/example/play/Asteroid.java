package com.example.play;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Asteroid {
    private int radius = 2; // радиус
    private float minSpeed = (float) 0.1; // минимальная скорость
    private float maxSpeed = (float) 0.5; // максимальная скорость
    public Asteroid(Context context) {
        Random random = new Random();
        int x,y;

        y=0;
        x = random.nextInt(GameView.maxX) - radius;
        int size = radius * 2;
        Context context1 = context;
        float speed = minSpeed + (maxSpeed - minSpeed) * random.nextFloat();

    }
    @Override
    public void update() {
        y += speed;
    }
    public boolean isCollision(float shipX, float shipY, float shipSize) {
        boolean x;
        x = false;
        Object size;
        size = null;
        return !(((x+size) < shipX)||(x > (shipX+shipSize))||((y+size) < shipY)||(y > (shipY+shipSize)));
    }

    public void draw(Paint paint, Canvas canvas) {
    }
}
