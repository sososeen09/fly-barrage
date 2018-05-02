package com.sososeen09.library;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;

/**
 * Created by yunlong.su on 2018/5/2.
 */

public class DrawHelper {

    public static Paint PAINT, PAINT_FPS;

    public static RectF RECT;

    private static boolean USE_DRAWCOLOR_TO_CLEAR_CANVAS = true;

    private static boolean USE_DRAWCOLOR_MODE_CLEAR = true;

    static {
        PAINT = new Paint();
        PAINT.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        PAINT.setColor(Color.TRANSPARENT);
        RECT = new RectF();
    }

    public static void useDrawColorToClearCanvas(boolean use, boolean useClearMode) {
        USE_DRAWCOLOR_TO_CLEAR_CANVAS = use;
        USE_DRAWCOLOR_MODE_CLEAR = useClearMode;
    }

    public static void drawFPS(Canvas canvas, String text) {
        if (PAINT_FPS == null) {
            PAINT_FPS = new Paint();
            PAINT_FPS.setColor(Color.RED);
            PAINT_FPS.setTextSize(30);
        }
        int top = canvas.getHeight() - 50;

        clearCanvas(canvas, 10, top - 50, (int) (PAINT_FPS.measureText(text) + 20), canvas.getHeight());
        canvas.drawText(text, 10, top, PAINT_FPS);
    }

    public static void clearCanvas(Canvas canvas) {
        if (USE_DRAWCOLOR_TO_CLEAR_CANVAS) {
            if (USE_DRAWCOLOR_MODE_CLEAR) {
                canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
            } else {
                canvas.drawColor(Color.TRANSPARENT);
            }
        } else {
            RECT.set(0, 0, canvas.getWidth(), canvas.getHeight());
            clearCanvas(canvas, RECT);
        }
    }

    public static void fillTransparent(Canvas canvas) {
        canvas.drawColor(0x00000000, PorterDuff.Mode.CLEAR);
    }

    public static void clearCanvas(Canvas canvas, float left, float top, float right, float bottom) {
        RECT.set(left, top, right, bottom);
        clearCanvas(canvas, RECT);
    }

    private static void clearCanvas(Canvas canvas, RectF rect) {
        if (rect.width() <= 0 || rect.height() <= 0) {
            return;
        }
        canvas.drawRect(rect, PAINT);
    }
}
