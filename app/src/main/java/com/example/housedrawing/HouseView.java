package com.example.housedrawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class HouseView extends View {

    private Paint paint;
    private Path roofPath;
    private boolean isNightMode = false;

    public HouseView(Context context) {
        super(context);
        init();
    }

    public HouseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        roofPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Небо
        paint.setColor(isNightMode ? Color.rgb(20, 24, 82) : Color.rgb(135, 206, 235));
        canvas.drawRect(0, 0, getWidth(), getHeight(), paint);

        // Трава
        paint.setColor(isNightMode ? Color.rgb(0, 100, 0) : Color.rgb(34, 139, 34));
        canvas.drawRect(0, getHeight() * 0.6f, getWidth(), getHeight(), paint);

        // Солнце или луна
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(100, 100, 50, paint);

        // Облако (несколько белых кругов)
        if (!isNightMode) {
            paint.setColor(Color.WHITE);
            canvas.drawCircle(300, 120, 30, paint);
            canvas.drawCircle(330, 110, 35, paint);
            canvas.drawCircle(360, 120, 30, paint);
            canvas.drawCircle(330, 130, 28, paint);
        }

        // Дом
        float left = 200;
        float top = getHeight() * 0.6f - 200;
        float right = left + 250;
        float bottom = getHeight() * 0.6f;

        paint.setColor(isNightMode ? Color.rgb(139, 69, 19) : Color.rgb(222, 184, 135));
        canvas.drawRect(left, top, right, bottom, paint);

        // Крыша
        paint.setColor(isNightMode ? Color.rgb(128, 0, 0) : Color.RED);
        roofPath.reset();
        roofPath.moveTo(left, top);
        roofPath.lineTo(left + 125, top - 100);
        roofPath.lineTo(right, top);
        roofPath.close();
        canvas.drawPath(roofPath, paint);

        // Дверь
        paint.setColor(Color.rgb(139, 69, 19));
        float doorWidth = 60;
        float doorHeight = 100;
        float doorLeft = left + 95;
        float doorTop = bottom - doorHeight;
        canvas.drawRect(doorLeft, doorTop, doorLeft + doorWidth, bottom, paint);

        // Ручка
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(doorLeft + doorWidth - 10, doorTop + 50, 5, paint);

        // Окна
        paint.setColor(isNightMode ? Color.YELLOW : Color.CYAN);
        float windowSize = 50;
        canvas.drawRect(left + 30, top + 30, left + 30 + windowSize, top + 30 + windowSize, paint);
        canvas.drawRect(right - 80, top + 30, right - 80 + windowSize, top + 30 + windowSize, paint);

        // Дерево — теперь ближе и больше
        float treeBaseY = getHeight() * 0.6f;
        float treeX = right + 100; // ближе к дому
        float treeTrunkWidth = 25;
        float treeTrunkHeight = 120;

        // Ствол
        paint.setColor(Color.rgb(139, 69, 19));
        canvas.drawRect(treeX, treeBaseY - treeTrunkHeight, treeX + treeTrunkWidth, treeBaseY, paint);

        // Крона дерева
        paint.setColor(isNightMode ? Color.rgb(0, 80, 0) : Color.GREEN);
        canvas.drawCircle(treeX + 10, treeBaseY - treeTrunkHeight - 20, 50, paint);
        canvas.drawCircle(treeX + 40, treeBaseY - treeTrunkHeight, 45, paint);
        canvas.drawCircle(treeX - 20, treeBaseY - treeTrunkHeight, 45, paint);
    }

    public void toggleTheme(boolean nightMode) {
        isNightMode = nightMode;
        invalidate();
    }
}
