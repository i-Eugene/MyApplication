package com.ieugene.customrecyclerview.dprecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ieugene.customrecyclerview.R;


public class CustomRecyclerView extends RecyclerView {
    private PaintFlagsDrawFilter drawFilter;
    private RectF rectF = new RectF();
    private Paint paint;
    private PorterDuffXfermode porterDuffXfermode;
    private float radius;

    public CustomRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomRecyclerView);
        radius = typedArray.getDimension(R.styleable.CustomRecyclerView_radius, 0);
        typedArray.recycle();

        drawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        setLayerType(LAYER_TYPE_HARDWARE, null);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        porterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        drawCorner(canvas);
    }

    private void drawCorner(Canvas canvas) {
        canvas.setDrawFilter(drawFilter);
        int sc = canvas.saveLayer(0F, 0F, canvas.getWidth(), canvas.getHeight(), null, Canvas.ALL_SAVE_FLAG);
        super.dispatchDraw(canvas);
        Bitmap mask = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas newCanvas = new Canvas(mask);
        newCanvas.drawRoundRect(rectF, radius, radius, paint);
        paint.setXfermode(porterDuffXfermode);
        canvas.drawBitmap(mask, 0F, 0F, paint);
        paint.setXfermode(null);
        canvas.restoreToCount(sc);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF.set(0, 0, w, h);
    }
}
