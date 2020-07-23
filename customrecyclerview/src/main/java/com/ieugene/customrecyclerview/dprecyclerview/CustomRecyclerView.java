package com.ieugene.customrecyclerview.dprecyclerview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.ieugene.customrecyclerview.R;


public class CustomRecyclerView extends RecyclerView {
    private Path path;
    private PaintFlagsDrawFilter drawFilter;
    private RectF rectF = new RectF();
    private float[] radii;

    public CustomRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DPRecyclerView);
        float radius = typedArray.getDimension(R.styleable.DPRecyclerView_radius, 0);
        typedArray.recycle();
        path = new Path();
        drawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        setLayerType(LAYER_TYPE_HARDWARE, null);
        radii = new float[8];
        for (int i = 0; i < 8; i++) {
            radii[i] = radius;
        }
    }

    @Override
    public void onDraw(Canvas c) {
        path.reset();
        path.addRoundRect(rectF, radii, Path.Direction.CW);
        c.setDrawFilter(drawFilter);
        c.clipPath(path);
        super.onDraw(c);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF.set(0, 0, w, h);
    }
}
