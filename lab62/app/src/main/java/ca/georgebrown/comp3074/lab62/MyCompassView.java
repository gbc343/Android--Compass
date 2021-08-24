package ca.georgebrown.comp3074.lab62;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class MyCompassView extends View {

    private Paint paint;


    private float position = 0;
    public MyCompassView(Context context){
        super(context);
        init();
    }

    private void init(){
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setTextSize(25);
        paint.setColor(Color.BLACK);
    }

    public void updateData(float position){
        this.position = position;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int xPoint = getMeasuredWidth()/2;
        int yPoint = getMeasuredHeight()/2;

        float radius = (float)(Math.max(xPoint,yPoint)*0.6);
        canvas.drawCircle(xPoint,yPoint, radius,paint);
        canvas.drawRect(2,2,getMeasuredWidth()-2,getMeasuredHeight()-2,paint);
        canvas.drawLine(xPoint,yPoint,
                (float)(xPoint-radius
                *Math.cos((double)(position+Math.PI/2))),
                (float)(yPoint- radius *
                        Math.sin((double)(position+Math.PI/2))),
        paint);
        canvas.drawText(String.valueOf(position),xPoint,yPoint,paint);
    }
}
