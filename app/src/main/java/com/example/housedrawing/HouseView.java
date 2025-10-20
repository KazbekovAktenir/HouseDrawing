package com.example.housedrawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class HouseView extends View {

    private Paint paint;
    private Path roofPath;
    private boolean isNightMode = false;
    private Random random;
    
    // Цвета для дневного и ночного режима
    private int skyColorDay = Color.parseColor("#87CEEB");
    private int skyColorNight = Color.parseColor("#191970");
    private int grassColorDay = Color.parseColor("#32CD32");
    private int grassColorNight = Color.parseColor("#006400");
    private int houseColorDay = Color.parseColor("#DEB887");
    private int houseColorNight = Color.parseColor("#8B4513");
    private int roofColorDay = Color.parseColor("#DC143C");
    private int roofColorNight = Color.parseColor("#8B0000");
    private int windowColorDay = Color.parseColor("#87CEEB");
    private int windowColorNight = Color.parseColor("#FFD700");
    private int doorColorDay = Color.parseColor("#8B4513");
    private int doorColorNight = Color.parseColor("#654321");

    public HouseView(Context context) {
        super(context);
        init();
    }

    public HouseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HouseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        roofPath = new Path();
        random = new Random();
    }
    

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        // Проверяем, что View имеет корректные размеры
        if (getWidth() <= 0 || getHeight() <= 0) {
            return;
        }
        
        try {
            drawSky(canvas);
            drawClouds(canvas);
            drawSunMoon(canvas);
            drawGrass(canvas);
            drawHouse(canvas);
            drawTrees(canvas);
        } catch (Exception e) {
            // В случае ошибки просто рисуем простой фон
            paint.setColor(Color.WHITE);
            canvas.drawRect(0, 0, getWidth(), getHeight(), paint);
        }
    }
    
    private void drawSky(Canvas canvas) {
        int skyColor = isNightMode ? skyColorNight : skyColorDay;
        
        // Градиентное небо
        float skyHeight = getHeight() * 0.6f;
        LinearGradient skyGradient = new LinearGradient(
            0, 0, 0, skyHeight,
            skyColor,
            isNightMode ? Color.parseColor("#000080") : Color.parseColor("#FFE4B5"),
            Shader.TileMode.CLAMP
        );
        paint.setShader(skyGradient);
        canvas.drawRect(0, 0, getWidth(), skyHeight, paint);
        paint.setShader(null);
    }
    
    private void drawClouds(Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setAlpha(isNightMode ? 100 : 200);
        
        // Статичные облака
        drawCloud(canvas, 200, 100, 40);
        drawCloud(canvas, 400, 80, 35);
        drawCloud(canvas, 600, 120, 45);
        drawCloud(canvas, 800, 90, 30);
        
        paint.setAlpha(255);
    }
    
    private void drawCloud(Canvas canvas, float x, float y, float size) {
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, size, paint);
        canvas.drawCircle(x + size * 0.8f, y, size * 0.8f, paint);
        canvas.drawCircle(x + size * 1.6f, y, size * 0.9f, paint);
        canvas.drawCircle(x + size * 0.4f, y - size * 0.3f, size * 0.7f, paint);
        canvas.drawCircle(x + size * 1.2f, y - size * 0.2f, size * 0.6f, paint);
    }
    
    private void drawSunMoon(Canvas canvas) {
        if (isNightMode) {
            // Луна
            paint.setColor(Color.parseColor("#F5F5DC"));
            canvas.drawCircle(100, 100, 60, paint);
            
            // Статичные звезды
            paint.setColor(Color.WHITE);
            canvas.drawCircle(50, 50, 2, paint);
            canvas.drawCircle(150, 30, 2, paint);
            canvas.drawCircle(80, 80, 2, paint);
            canvas.drawCircle(200, 60, 2, paint);
            canvas.drawCircle(120, 40, 2, paint);
        } else {
            // Солнце с градиентом
            RadialGradient sunGradient = new RadialGradient(
                100, 100, 80,
                Color.YELLOW,
                Color.parseColor("#FFA500"),
                Shader.TileMode.CLAMP
            );
            paint.setShader(sunGradient);
            canvas.drawCircle(100, 100, 80, paint);
            paint.setShader(null);
        }
    }
    
    private void drawGrass(Canvas canvas) {
        int grassColor = isNightMode ? grassColorNight : grassColorDay;
        paint.setColor(grassColor);
        paint.setStyle(Paint.Style.FILL);
        
        float grassStartY = getHeight() * 0.6f;
        canvas.drawRect(0, grassStartY, getWidth(), getHeight(), paint);
        
        // Статичная трава
        paint.setColor(isNightMode ? Color.parseColor("#228B22") : Color.parseColor("#90EE90"));
        for (int i = 0; i < 20; i++) {
            float x = i * 50 + 25;
            float y = grassStartY + 5;
            canvas.drawRect(x, y, x + 2, y + 10, paint);
        }
    }
    
    private void drawHouse(Canvas canvas) {
        float houseWidth = 300;
        float houseHeight = 200;
        float houseLeft = 50;
        float houseTop = getHeight() * 0.6f - houseHeight;
        
        // Тень дома
        paint.setColor(Color.parseColor("#40000000"));
        canvas.drawRect(houseLeft + 5, houseTop + 5, houseLeft + houseWidth + 5, houseTop + houseHeight + 5, paint);
        
        // Основание дома
        int houseColor = isNightMode ? houseColorNight : houseColorDay;
        paint.setColor(houseColor);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(houseLeft, houseTop, houseLeft + houseWidth, houseTop + houseHeight, paint);
        
        // Крыша с градиентом
        int roofColor = isNightMode ? roofColorNight : roofColorDay;
        LinearGradient roofGradient = new LinearGradient(
            houseLeft, houseTop - 100,
            houseLeft, houseTop,
            roofColor,
            Color.parseColor("#8B0000"),
            Shader.TileMode.CLAMP
        );
        paint.setShader(roofGradient);
        roofPath.reset();
        roofPath.moveTo(houseLeft, houseTop);
        roofPath.lineTo(houseLeft + houseWidth / 2, houseTop - 100);
        roofPath.lineTo(houseLeft + houseWidth, houseTop);
        roofPath.close();
        canvas.drawPath(roofPath, paint);
        paint.setShader(null);
        
        // Дымоход
        paint.setColor(Color.parseColor("#696969"));
        canvas.drawRect(houseLeft + houseWidth - 60, houseTop - 80, houseLeft + houseWidth - 40, houseTop - 40, paint);
        
        // Дым
        paint.setColor(Color.parseColor("#C0C0C0"));
        paint.setAlpha(150);
        for (int i = 0; i < 3; i++) {
            float smokeX = houseLeft + houseWidth - 50 + i * 5;
            float smokeY = houseTop - 80 - i * 10;
            canvas.drawCircle(smokeX, smokeY, 8 - i * 2, paint);
        }
        paint.setAlpha(255);
        
        // Окна
        drawWindows(canvas, houseLeft, houseTop, houseWidth, houseHeight);
        
        // Дверь
        drawDoor(canvas, houseLeft, houseTop, houseWidth, houseHeight);
    }
    
    private void drawWindows(Canvas canvas, float houseLeft, float houseTop, float houseWidth, float houseHeight) {
        int windowColor = isNightMode ? windowColorNight : windowColorDay;
        
        // Левое окно
        float windowSize = 40;
        float leftWindowX = houseLeft + 30;
        float leftWindowY = houseTop + 30;
        
        // Рама окна
        paint.setColor(Color.parseColor("#8B4513"));
        canvas.drawRect(leftWindowX - 2, leftWindowY - 2, leftWindowX + windowSize + 2, leftWindowY + windowSize + 2, paint);
        
        // Стекло окна
        if (isNightMode) {
            paint.setColor(Color.parseColor("#FFD700"));
        } else {
            paint.setColor(windowColor);
        }
        canvas.drawRect(leftWindowX, leftWindowY, leftWindowX + windowSize, leftWindowY + windowSize, paint);
        
        // Крестовина окна
        paint.setColor(Color.parseColor("#8B4513"));
        paint.setStrokeWidth(2);
        canvas.drawLine(leftWindowX + windowSize/2, leftWindowY, leftWindowX + windowSize/2, leftWindowY + windowSize, paint);
        canvas.drawLine(leftWindowX, leftWindowY + windowSize/2, leftWindowX + windowSize, leftWindowY + windowSize/2, paint);
        
        // Правое окно
        float rightWindowX = houseLeft + houseWidth - 70;
        float rightWindowY = houseTop + 30;
        
        // Рама окна
        paint.setColor(Color.parseColor("#8B4513"));
        canvas.drawRect(rightWindowX - 2, rightWindowY - 2, rightWindowX + windowSize + 2, rightWindowY + windowSize + 2, paint);
        
        // Стекло окна
        if (isNightMode) {
            paint.setColor(Color.parseColor("#FFD700"));
        } else {
            paint.setColor(windowColor);
        }
        canvas.drawRect(rightWindowX, rightWindowY, rightWindowX + windowSize, rightWindowY + windowSize, paint);
        
        // Крестовина окна
        paint.setColor(Color.parseColor("#8B4513"));
        canvas.drawLine(rightWindowX + windowSize/2, rightWindowY, rightWindowX + windowSize/2, rightWindowY + windowSize, paint);
        canvas.drawLine(rightWindowX, rightWindowY + windowSize/2, rightWindowX + windowSize, rightWindowY + windowSize/2, paint);
        
        paint.setStyle(Paint.Style.FILL);
    }
    
    private void drawDoor(Canvas canvas, float houseLeft, float houseTop, float houseWidth, float houseHeight) {
        int doorColor = isNightMode ? doorColorNight : doorColorDay;
        
        float doorWidth = 50;
        float doorHeight = 80;
        float doorX = houseLeft + houseWidth / 2 - doorWidth / 2;
        float doorY = houseTop + houseHeight - doorHeight;
        
        // Дверь
        paint.setColor(doorColor);
        canvas.drawRect(doorX, doorY, doorX + doorWidth, doorY + doorHeight, paint);
        
        // Ручка двери
        paint.setColor(Color.parseColor("#FFD700"));
        canvas.drawCircle(doorX + doorWidth - 10, doorY + doorHeight / 2, 3, paint);
    }
    
    private void drawTrees(Canvas canvas) {
        float groundLevel = getHeight() * 0.6f;
        
        // Дерево 1
        float tree1X = getWidth() - 150;
        float tree1Y = groundLevel - 50;
        
        // Ствол
        paint.setColor(Color.parseColor("#8B4513"));
        canvas.drawRect(tree1X, tree1Y, tree1X + 20, tree1Y + 100, paint);
        
        // Крона
        paint.setColor(isNightMode ? Color.parseColor("#006400") : Color.parseColor("#228B22"));
        canvas.drawOval(tree1X - 30, tree1Y - 40, tree1X + 50, tree1Y + 20, paint);
        
        // Дерево 2
        float tree2X = getWidth() - 80;
        float tree2Y = groundLevel - 30;
        
        // Ствол
        paint.setColor(Color.parseColor("#8B4513"));
        canvas.drawRect(tree2X, tree2Y, tree2X + 15, tree2Y + 80, paint);
        
        // Крона
        paint.setColor(isNightMode ? Color.parseColor("#006400") : Color.parseColor("#228B22"));
        canvas.drawOval(tree2X - 25, tree2Y - 30, tree2X + 40, tree2Y + 15, paint);
    }
    
    
    public void toggleTheme(boolean nightMode) {
        isNightMode = nightMode;
        invalidate();
    }
    
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Принудительно перерисовываем при изменении размера
        if (w > 0 && h > 0) {
            invalidate();
        }
    }
}
