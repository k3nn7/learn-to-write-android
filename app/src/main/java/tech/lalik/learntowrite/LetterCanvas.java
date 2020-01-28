package tech.lalik.learntowrite;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class LetterCanvas extends View {
    private Path path;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private final float TOUCH_TOLERANCE = 4;
    private float startX, startY;

    public LetterCanvas(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        this.path = new Path();
        this.paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(20);
    }

    public void clear() {
        this.canvas.drawColor(Color.WHITE);;
    }

    public Bitmap getBitmap() {
        return this.bitmap;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        this.bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
        this.bitmap.eraseColor(Color.WHITE);
        this.canvas = new Canvas(this.bitmap);
        this.canvas.drawColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(this.bitmap, 0, 0, paint);
        canvas.drawPath(this.path, this.paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.onTouchDown(x, y);
                this.invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                this.onTouchMove(x, y);
                this.invalidate();
                break;
            case MotionEvent.ACTION_UP:
                this.onTouchUp();
                this.invalidate();
                break;
        }

        return true;
    }

    private void onTouchDown(float x, float y) {
        this.path.reset();
        this.path.moveTo(x, y);
        this.startX = x;
        this.startY = y;
    }

    private void onTouchMove(float x, float y) {
        float dx = Math.abs(x - this.startX);
        float dy = Math.abs(y - this.startY);
        if (dx >= this.TOUCH_TOLERANCE || dy >= this.TOUCH_TOLERANCE) {
            this.path.quadTo(startX, startY, (startX + x) / 2, (startY + y) / 2);
            startX = x;
            startY = y;
        }
    }

    private void onTouchUp() {
        this.canvas.drawPath(this.path, this.paint);
        this.path.reset();
    }
}
