package com.example.play;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import java.security.AccessControlContext;

public class Ship {
    public float x;
    public float y;
    public float size;

    public Ship(Context context) {
        bitmapId = R.drawable.ship; // определяем начальные параметры
        size = 5;
        x=7;
        y=GameView.maxY - size - 1;
        speed = (float) 0.2;

        init(context); // инициализируем корабль
    }



    @Override
    public void update() { // перемещаем корабль в зависимости от нажатой кнопки
        int speed = 0,x = 0;
        if(MainActivity.isLeftPressed && x >= 0){
            x -= speed;
        }
        if(MainActivity.isRightPressed && x <= GameView.maxX - 5){
            x += speed;
        }
    }

    public void draw(Paint paint, Canvas canvas) {
    }
}
