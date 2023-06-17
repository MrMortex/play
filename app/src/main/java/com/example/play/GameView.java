package com.example.play;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.View;

import java.util.ArrayList;

public class GameView extends View {
    public static int maxX = 20; // размер по горизонтали
    public static int maxY = 28; // размер по вертикали
    public static float unitW = 0; // пикселей в юните по горизонтали
    public static float unitH = 0; // пикселей в юните по вертикали
    private boolean firstTime = true;
    private boolean gameRunning = true;
    private Ship ship;
    private Thread gameThread = null;
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    public GameView(Context context) {
        super(context);
        //инициализируем обьекты для рисования
        surfaceHolder = getHolder();
        paint = new Paint();

        // инициализируем поток
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        while (gameRunning) {
            update();
            draw();
            control();
            checkCollision();
            checkIfNewAsteroid();
        }
    }

    private void update() {
        if(!firstTime) {
            ship.update();
            for (Asteroid asteroid : asteroids) {
                asteroid.update();
            }
        }
    }

    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {  //проверяем валидный ли surface

            if(firstTime){ // инициализация при первом запуске
                firstTime = false;
                unitW = surfaceHolder.getSurfaceFrame().width()/maxX; // вычисляем число пикселей в юните
                unitH = surfaceHolder.getSurfaceFrame().height()/maxY;

                ship = new Ship(getContext()); // добавляем корабль
            }

            canvas = surfaceHolder.lockCanvas(); // закрываем canvas
            canvas.drawColor(Color.BLACK); // заполняем фон чёрным

            ship.draw(paint, canvas); // рисуем корабль

            for(Asteroid asteroid: asteroids){ // рисуем астероиды
                asteroid.draw(paint, canvas);
            }

            surfaceHolder.unlockCanvasAndPost(canvas); // открываем canvas
        }
    }

    private void control() { // пауза на 17 миллисекунд
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    private ArrayList<Asteroid> asteroids = new ArrayList<>(); // тут будут харанится астероиды
    private final int ASTEROID_INTERVAL = 50; // время через которое появляются астероиды (в итерациях)
    private int currentTime = 0;
    private void checkCollision(){ // перебираем все астероиды и проверяем не касается ли один из них корабля
        int x,y,size;
        for (Asteroid asteroid : asteroids) {
            if(asteroid.isCollision(ship.x, ship.y, ship.size)){
                // игрок проиграл
                gameRunning = false; // останавливаем игру
                // TODO добавить анимацию взрыва
            }
        }
    }

    private void checkIfNewAsteroid(){ // каждые 50 итераций добавляем новый астероид
        if(currentTime >= ASTEROID_INTERVAL){
            Asteroid asteroid = new Asteroid(getContext());
            asteroids.add(asteroid);
            currentTime = 0;
        }else{
            currentTime ++;
        }
    }
}
