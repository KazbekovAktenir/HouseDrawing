package com.example.housedrawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;



public class HouseView extends View {

    private Paint paint;  //создаем глобальные объекты
    private Path roofPath;

    public HouseView(Context context) {
        super(context);
        init();  //инициализация объектов
    }

    private void init() {
        paint = new Paint();
        roofPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //газон
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 700, getWidth(), getHeight(), paint);

        //основание дома
        paint.setColor(Color.parseColor("#8B4513"));
        canvas.drawRect(130, 330, 530, 730, paint);

        //крыша
        paint.setColor(Color.RED);
        roofPath.reset();  //обнуляем путь перед повторным использованием
        roofPath.moveTo(130, 330);
        roofPath.lineTo(330, 130);
        roofPath.lineTo(530, 330);
        roofPath.close();
        canvas.drawPath(roofPath, paint);

        //дерево
        paint.setColor(Color.parseColor("#8B4513"));
        canvas.drawRect(800, 300, 850, 700, paint);
        paint.setColor(Color.GREEN);
        canvas.drawOval(750, 200, 900, 500, paint);

        //дверь и окна
        paint.setColor(Color.BLUE);
        canvas.drawRect(280, 550, 380, 730, paint);
        canvas.drawRect(180, 430, 280, 530, paint);
        canvas.drawRect(380, 430, 480, 530, paint);

        //дымоход
        paint.setColor(Color.GRAY);
        canvas.drawRect(410, 230, 450, 330, paint);

        //солнце
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(80, 80, 80, paint);
    }
}
